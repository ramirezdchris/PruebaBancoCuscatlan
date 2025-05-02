package com.bankcuscatlan.payment.strategy;

import com.bankcuscatlan.payment.model.dto.payment.PaymentResult;
import com.bankcuscatlan.payment.model.request.RequestPaymentDTO;
import org.springframework.stereotype.Component;

@Component("CREDIT_CARD")
public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResult processPayment(RequestPaymentDTO request) {
        return new PaymentResult("SUCCESS", request.getTotal().doubleValue(), "Credit Card Payment");
    }
}
