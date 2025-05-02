package com.bankcuscatlan.orders.controller;


import com.bankcuscatlan.orders.model.dto.response.ResponseDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseOrderDTO;
import com.bankcuscatlan.orders.service.IOrder;
import com.bankcuscatlan.orders.model.dto.request.RequestOrderDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
@Slf4j
public class OrderController {

    private final IOrder orderImp;

    @GetMapping(path = "/health-check")
    public String healthCheck(){
        return "Products";
    }

    @GetMapping(path = "/order/{id}")
    public ResponseEntity<ResponseDTO> getOrderById(@PathVariable Long id){
        return new ResponseEntity<>(
                orderImp.getOrder(id),
                HttpStatus.OK);
    }

    @PostMapping(path = "/order")
    public Mono<ResponseEntity<ResponseDTO<ResponseOrderDTO>>> createOrder(@Valid @RequestBody RequestOrderDTO requestOrderDTO) {
        log.info("Received request with customerId: {}", requestOrderDTO.getCustomerId());
        return orderImp.createOrder(requestOrderDTO)
                .map(response -> ResponseEntity
                        .status(response.getCode())
                        .body(response));
    }

    @PutMapping(path = "/order/{id}")
    public Mono<ResponseEntity<ResponseDTO<ResponseOrderDTO>>> updateOrder(@PathVariable Long id, @Valid @RequestBody RequestOrderDTO requestOrderDTO) {
        log.info("Received request with customerId: {}", requestOrderDTO.getCustomerId());
        return orderImp.updateOrder(id, requestOrderDTO)
                .map(response -> ResponseEntity
                        .status(response.getCode())
                        .body(response));
    }

    @DeleteMapping(path = "/order/{id}")
    public ResponseEntity<ResponseDTO<Void>> updateOrder(@PathVariable Long id) {
        log.info("Delete order: {}", id);
        return new ResponseEntity<>(
                orderImp.deleteOrder(id),
                HttpStatus.ACCEPTED);
    }
}
