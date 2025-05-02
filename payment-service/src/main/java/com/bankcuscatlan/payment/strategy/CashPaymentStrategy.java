package com.bankcuscatlan.payment.strategy;

import com.bankcuscatlan.payment.model.dto.payment.PaymentResult;
import com.bankcuscatlan.payment.model.request.RequestPaymentDTO;
import org.springframework.stereotype.Component;

@Component("CASH")
public class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResult processPayment(RequestPaymentDTO request) {
        return new PaymentResult("SUCCESS", request.getTotal().doubleValue(), "Cash Payment");
    }
}


