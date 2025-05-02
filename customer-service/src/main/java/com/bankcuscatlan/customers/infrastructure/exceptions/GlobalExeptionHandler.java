package com.bankcuscatlan.customers.infrastructure.exceptions;

import com.bankcuscatlan.customers.model.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleNotFound(CustomerNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO("CUSTOMER_NOT_FOUND", ex.getMessage(), null) );
    }


    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO("INTERNAL_SERVER_ERROR", ex.getMessage(), null) );
    }
}
