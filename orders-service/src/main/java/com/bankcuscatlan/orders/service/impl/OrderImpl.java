package com.bankcuscatlan.orders.service.impl;


import com.bankcuscatlan.orders.infrastructure.config.Client.CustomersClient;
import com.bankcuscatlan.orders.infrastructure.config.Client.PaymentClient;
import com.bankcuscatlan.orders.infrastructure.config.Client.ProductsClient;
import com.bankcuscatlan.orders.infrastructure.exceptions.NotFoundException;
import com.bankcuscatlan.orders.infrastructure.mapper.OrderMapper;
import com.bankcuscatlan.orders.infrastructure.repository.OrderRepository;
import com.bankcuscatlan.orders.model.dto.payment.RequestPaymentDTO;
import com.bankcuscatlan.orders.model.dto.payment.ResponsePaymentDTO;
import com.bankcuscatlan.orders.model.dto.product.ProductDTO;
import com.bankcuscatlan.orders.model.dto.request.FilterProduct;
import com.bankcuscatlan.orders.model.dto.request.RequestOrderDTO;
import com.bankcuscatlan.orders.model.dto.request.RequestProductOrderDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseOrderDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseOrderDetailDTO;
import com.bankcuscatlan.orders.model.entity.Order;
import com.bankcuscatlan.orders.model.entity.OrderDetail;
import com.bankcuscatlan.orders.service.IOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderImpl implements IOrder {

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final CustomersClient customerClient;
    private final ProductsClient productClient;
    private final PaymentClient paymentClient;


    @Override
    public ResponseDTO getOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        ResponseOrderDTO orderDTO;
        if (order.isPresent()) {
            orderDTO = mapper.toResponseDto(order.get());
            return new ResponseDTO<>(HttpStatus.OK, "SUCCESS", orderDTO);
        } else {
            throw new NotFoundException("Order not found");
        }
    }


    @Override
    public Mono<ResponseDTO<ResponseOrderDTO>> updateOrder(Long id, RequestOrderDTO request) {
        List<RequestProductOrderDTO> groupedProducts = groupProducts(request.getProducts());
        orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order with ID " + id + " not found"));

        return Mono.just(orderRepository.findById(id))
                .switchIfEmpty(Mono.error(new NotFoundException("Order with ID " + id + " not found")))
                .flatMap(existingOrder ->
                        customerClient.getCustomerById(request.getCustomerId())
                                .switchIfEmpty(Mono.error(new NotFoundException("Customer not found")))
                                .flatMap(customer ->

                                        Flux.fromIterable(groupedProducts)
                                                .flatMap(reqProduct -> productClient.getProductById(reqProduct.getProductid())
                                                        .map(productDTO -> enrichValidProduct(productDTO, reqProduct))
                                                        .onErrorResume(e -> Mono.just(buildNotFoundProduct(reqProduct)))
                                                )
                                                .collectList()
                                                .flatMap(resolvedProducts -> {
                                                    // separar válidos e inválidos
                                                    List<FilterProduct> validProducts = resolvedProducts.stream()
                                                            .filter(p -> p.getPrice() != -1)
                                                            .toList();

                                                    // construir DTO parcial
                                                    List<RequestProductOrderDTO> validProductRequests = validProducts.stream()
                                                            .map(p -> new RequestProductOrderDTO(p.getProductid(), p.getQuantity()))
                                                            .toList();

                                                    Map<Long, OrderDetail> existingDetailsMap = existingOrder.get().getItems().stream()
                                                            .collect(Collectors.toMap(OrderDetail::getProductId, d -> d));

                                                    for (RequestProductOrderDTO p : validProductRequests) {
                                                        OrderDetail detail = existingDetailsMap.get(p.getProductid());
                                                        if (detail != null) {
                                                            detail.setQuantity(p.getQuantity());
                                                            detail.setProductPrice(0.0); // actualiza si deseas
                                                        } else {
                                                            OrderDetail newDetail = new OrderDetail();
                                                            newDetail.setProductId(p.getProductid());
                                                            newDetail.setQuantity(p.getQuantity());
                                                            newDetail.setProductPrice(0.0);
                                                            newDetail.setOrder(existingOrder.get());
                                                            existingOrder.get().getItems().add(newDetail);
                                                        }
                                                    }

                                                    //existingOrder.get().setItems(newDetails);
                                                    existingOrder.get().setCustomerId(request.getCustomerId());
                                                    existingOrder.get().setDateUpdated(LocalDateTime.now());

                                                    orderRepository.save(existingOrder.get());

                                                    // construir respuesta
                                                    List<ResponseOrderDetailDTO> responseProducts = resolvedProducts.stream()
                                                            .map(resolved -> ResponseOrderDetailDTO.builder()
                                                                    .id(resolved.getProductid())
                                                                    .message(resolved.getMessage())
                                                                    .price(resolved.getPrice())
                                                                    .quantity(resolved.getQuantity())
                                                                    .build())
                                                            .toList();

                                                    ResponseOrderDTO response = ResponseOrderDTO.builder()
                                                            .orderid(existingOrder.get().getId())
                                                            .customerid(existingOrder.get().getCustomerId())
                                                            .products(responseProducts)
                                                            .build();

                                                    List<Long> notFoundIds = resolvedProducts.stream()
                                                            .filter(p -> p.getPrice() == -1)
                                                            .map(FilterProduct::getProductid)
                                                            .toList();

                                                    String message = "ORDER UPDATED";
                                                    if (!notFoundIds.isEmpty()) {
                                                        message += " - PRODUCT_ID NOT FOUNDS: " + notFoundIds.toString().replaceAll("[\\[\\]]", "");
                                                    }

                                                    return Mono.just(ResponseDTO.<ResponseOrderDTO>builder()
                                                            .code(HttpStatus.OK)
                                                            .message(message)
                                                            .data(response)
                                                            .build());
                                                })
                                )
                );
    }



    @Override
    public Mono<ResponseDTO<ResponseOrderDTO>> createOrder(RequestOrderDTO request) {
        List<RequestProductOrderDTO> groupedProducts = groupProducts(request.getProducts());
        return customerClient.getCustomerById(request.getCustomerId())
                .switchIfEmpty(Mono.error(new NotFoundException("Customer not found")))
                .flatMap(customer -> {

                    List<RequestProductOrderDTO> requestedProducts = request.getProducts();

                    return Flux.fromIterable(groupedProducts)
                            .flatMap(reqProduct -> productClient.getProductById(reqProduct.getProductid())
                                    .map(productDTO -> enrichValidProduct(productDTO, reqProduct))
                                    .onErrorResume(e -> Mono.just(buildNotFoundProduct(reqProduct)))
                            )
                            .collectList()
                            .flatMap(resolvedProducts -> {
                                // separar válidos e inválidos
                                List<FilterProduct> validProducts = resolvedProducts.stream()
                                        .filter(p -> p.getPrice() != -1)
                                        .toList();

                                // construir DTO parcial para mapper
                                List<RequestProductOrderDTO> validProductRequests = validProducts.stream()
                                        .map(p -> new RequestProductOrderDTO(p.getProductid(), p.getQuantity()))
                                        .toList();

                                Order order = mapper.toEntity(
                                        RequestOrderDTO.builder()
                                                .customerId(request.getCustomerId())
                                                .products(validProductRequests)
                                                .build()
                                );

                                orderRepository.save(order);

                                // construir respuesta
                                List<ResponseOrderDetailDTO> responseProducts = resolvedProducts.stream()
                                        .map(resolved -> ResponseOrderDetailDTO.builder()
                                                .id(resolved.getProductid())
                                                .message(resolved.getMessage())
                                                .price(resolved.getPrice())
                                                .quantity(resolved.getQuantity())
                                                .build())
                                        .toList();

                                ResponseOrderDTO response = ResponseOrderDTO.builder()
                                        .orderid(order.getId())
                                        .customerid(order.getCustomerId())
                                        .products(responseProducts)
                                        .build();

                                // mensaje de productos no encontrados
                                List<Long> notFoundIds = resolvedProducts.stream()
                                        .filter(p -> p.getPrice() == -1)
                                        .map(FilterProduct::getProductid)
                                        .toList();

                                String message = "ORDER CREATED";
                                if (!notFoundIds.isEmpty()) {
                                    message += " - PRODUCT_ID NOT FOUNDS: " + notFoundIds.toString().replaceAll("[\\[\\]]", "");
                                }

                                return Mono.just(ResponseDTO.<ResponseOrderDTO>builder()
                                        .code(HttpStatus.CREATED)
                                        .message(message)
                                        .data(response)
                                        .build());
                            });
                });
    }

    @Override
    public ResponseDTO<Void> deleteOrder(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty()) {
            throw new NotFoundException("Order with ID " + id + " not found");
        }
        orderRepository.deleteById(id);

        return ResponseDTO.<Void>builder()
                .code(HttpStatus.NO_CONTENT)
                .message("ORDER DELETED")
                .data(null)
                .build();
    }

    @Override
    public ResponseDTO<ResponsePaymentDTO> payOrder(RequestPaymentDTO requestPaymentDTO) {
        Order order = orderRepository.findById(requestPaymentDTO.getOrderId())
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + requestPaymentDTO.getOrderId()));

        BigDecimal total = order.getItems().stream()
                .map(item -> BigDecimal.valueOf(item.getProductPrice())
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        RequestPaymentDTO paymentDTO = RequestPaymentDTO.builder()
                .orderId(order.getId())
                .total(total)
                .paymentType(requestPaymentDTO.getPaymentType())
                .build();


        ResponseDTO<ResponsePaymentDTO> paymentResponse = paymentClient.paymentOrder(paymentDTO);

        return ResponseDTO.<ResponsePaymentDTO>builder()
                .code(paymentResponse.getCode())
                .message(paymentResponse.getMessage())
                .data(paymentResponse.getData())
                .build();
    }




    private FilterProduct enrichValidProduct(ResponseDTO<ProductDTO> product, RequestProductOrderDTO originalRequest) {
        ProductDTO productDTO = product.getData();
        return FilterProduct.builder()
                .productid(productDTO.getId())
                .message(productDTO.getTitle())
                .price(productDTO.getPrice())
                .quantity(originalRequest.getQuantity())
                .build();
    }

    private FilterProduct buildNotFoundProduct(RequestProductOrderDTO originalRequest) {
        return FilterProduct.builder()
                .productid(originalRequest.getProductid())
                .message("Product Not found")
                .price(-1)
                .quantity(0)
                .build();
    }

    private List<RequestProductOrderDTO> groupProducts(List<RequestProductOrderDTO> products) {
        return products.stream()
                .collect(Collectors.toMap(
                        RequestProductOrderDTO::getProductid,
                        RequestProductOrderDTO::getQuantity,
                        Integer::sum
                ))
                .entrySet()
                .stream()
                .map(entry -> new RequestProductOrderDTO(entry.getKey(), entry.getValue()))
                .toList();
    }


}
