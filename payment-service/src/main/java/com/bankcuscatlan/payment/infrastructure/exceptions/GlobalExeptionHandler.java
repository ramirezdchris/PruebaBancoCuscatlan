package com.bankcuscatlan.payment.infrastructure.exceptions;

import com.bankcuscatlan.payment.model.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExeptionHandler {

    
    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null));
    }
}
