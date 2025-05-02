package com.bankcuscatlan.payment.strategy;

import com.bankcuscatlan.payment.model.dto.payment.PaymentResult;
import com.bankcuscatlan.payment.model.request.RequestPaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PaymentContext {

    private final Map<String, PaymentStrategy> strategies;

    @Autowired
    public PaymentContext(List<PaymentStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(s -> s.getClass().getAnnotation(Component.class).value(), s -> s));
    }

    public PaymentResult executePayment(RequestPaymentDTO request) {
        String type = request.getPaymentType();
        PaymentStrategy strategy = strategies.get(type.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid payment type: " + type);
        }
        return strategy.processPayment(request);
    }

}
