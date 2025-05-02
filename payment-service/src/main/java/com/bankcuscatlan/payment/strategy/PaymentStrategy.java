package com.bankcuscatlan.payment.strategy;

import com.bankcuscatlan.payment.model.dto.payment.PaymentResult;
import com.bankcuscatlan.payment.model.request.RequestPaymentDTO;

public interface PaymentStrategy {
    PaymentResult processPayment(RequestPaymentDTO request);
}

