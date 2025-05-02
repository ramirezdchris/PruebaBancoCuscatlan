package com.bankcuscatlan.orders.infrastructure.config.Client;

import com.bankcuscatlan.orders.model.dto.payment.RequestPaymentDTO;
import com.bankcuscatlan.orders.model.dto.payment.ResponsePaymentDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${payment-api.host}")
public interface PaymentClient {

    @PostMapping("${payment-api.path}")
    ResponseDTO<ResponsePaymentDTO> paymentOrder(@RequestBody RequestPaymentDTO dto);
}
