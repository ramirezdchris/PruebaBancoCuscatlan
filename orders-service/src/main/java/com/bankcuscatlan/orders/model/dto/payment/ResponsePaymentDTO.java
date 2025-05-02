package com.bankcuscatlan.orders.model.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePaymentDTO {
    private Long idPayment;
    private Long orderId;
    private BigDecimal total;
    private String paymentType;
    private String message;
}
