package com.bankcuscatlan.orders.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOrderDetailDTO {
    private Long id;

    private String message;
    private double price;

    private int quantity;
}
