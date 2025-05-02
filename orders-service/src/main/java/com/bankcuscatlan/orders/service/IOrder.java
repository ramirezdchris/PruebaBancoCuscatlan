package com.bankcuscatlan.orders.service;


import com.bankcuscatlan.orders.model.dto.payment.RequestPaymentDTO;
import com.bankcuscatlan.orders.model.dto.payment.ResponsePaymentDTO;
import com.bankcuscatlan.orders.model.dto.request.RequestOrderDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseOrderDTO;
import reactor.core.publisher.Mono;

public interface IOrder {

    ResponseDTO getOrder(Long id);
    Mono<ResponseDTO<ResponseOrderDTO>> createOrder(RequestOrderDTO requestOrderDTO);

    Mono<ResponseDTO<ResponseOrderDTO>> updateOrder(Long id, RequestOrderDTO request);

    ResponseDTO<Void> deleteOrder(Long id);

    ResponseDTO<ResponsePaymentDTO> payOrder(RequestPaymentDTO requestPaymentDTO);

}
