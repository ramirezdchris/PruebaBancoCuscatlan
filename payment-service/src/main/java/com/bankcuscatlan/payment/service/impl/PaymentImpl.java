package com.bankcuscatlan.payment.service.impl;


import com.bankcuscatlan.payment.infrastructure.mapper.PaymentMapper;
import com.bankcuscatlan.payment.infrastructure.repository.PaymentRepository;
import com.bankcuscatlan.payment.model.dto.payment.PaymentResult;
import com.bankcuscatlan.payment.model.dto.response.ResponseDTO;
import com.bankcuscatlan.payment.model.dto.response.ResponsePaymentDTO;
import com.bankcuscatlan.payment.model.entity.Payment;
import com.bankcuscatlan.payment.model.request.RequestPaymentDTO;
import com.bankcuscatlan.payment.service.IPayment;
import com.bankcuscatlan.payment.strategy.PaymentContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentImpl implements IPayment {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    private final PaymentContext context;


    @Override
    public ResponseDTO<PaymentResult> savePayment(RequestPaymentDTO requestPaymentDTO) {


        Payment entity = paymentMapper.toEntity(requestPaymentDTO);
        Payment saved = paymentRepository.save(entity);
        PaymentResult result = context.executePayment(requestPaymentDTO);
        ResponsePaymentDTO response = paymentMapper.toResponse(saved);

        return ResponseDTO.<PaymentResult>builder()
                .code(HttpStatus.CREATED)
                .message("SUCCESS")
                .data(result)
                .build();

    }




}
