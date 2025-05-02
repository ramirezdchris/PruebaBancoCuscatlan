package com.bankcuscatlan.payment.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePaymentDTO {
    private Long idPayment;
    private Long orderId;
    private double total;
    private String paymentType;
    private String message;
}
