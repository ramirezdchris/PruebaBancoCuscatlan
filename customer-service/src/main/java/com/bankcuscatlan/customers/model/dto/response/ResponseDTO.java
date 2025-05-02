package com.bankcuscatlan.customers.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    private String code;
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private T data;
}
