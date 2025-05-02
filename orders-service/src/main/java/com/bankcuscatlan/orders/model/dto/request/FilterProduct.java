package com.bankcuscatlan.orders.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterProduct {
    private Long productid;
    private String message;
    private double price;
    private int quantity;
}
