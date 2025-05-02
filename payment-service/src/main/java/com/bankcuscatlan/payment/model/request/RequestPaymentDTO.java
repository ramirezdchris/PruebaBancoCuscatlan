package com.bankcuscatlan.payment.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestPaymentDTO {
    private BigDecimal total;
    private Long orderId;
    private String paymentType;
}



