package com.bankcuscatlan.payment.controller;

import com.bankcuscatlan.payment.model.dto.response.ResponseDTO;
import com.bankcuscatlan.payment.model.dto.response.ResponsePaymentDTO;
import com.bankcuscatlan.payment.model.request.RequestPaymentDTO;
import com.bankcuscatlan.payment.service.IPayment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    private final IPayment paymentService;

    @PostMapping("payment")
    public ResponseEntity<ResponseDTO<ResponsePaymentDTO>> registerPayment(@RequestBody @Valid RequestPaymentDTO request) {
        ResponseDTO<ResponsePaymentDTO> response = paymentService.savePayment(request);
        return new ResponseEntity<>(response, response.getCode());
    }
}

