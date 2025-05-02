package com.bankcuscatlan.orders.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO<T> {
    private HttpStatus code;
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private T data;
}
