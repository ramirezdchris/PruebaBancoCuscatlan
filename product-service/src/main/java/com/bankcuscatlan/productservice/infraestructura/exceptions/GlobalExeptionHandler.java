package com.bankcuscatlan.productservice.infraestructura.exceptions;

import com.bankcuscatlan.productservice.model.dto.product.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;

@ControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleNotFound(ProductNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO("PRODUCT_NOT_FOUND", ex.getMessage(), null) );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleServiceUnavailable(ProductServiceUnavailableException ex){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ResponseDTO("FAKEAPI_SERVICE_DOWN", ex.getMessage(), null) );
    }

    /*@ExceptionHandler
    public ResponseEntity<ResponseDTO> handleException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO("INTERNAL_SERVER_ERROR", ex.getMessage(), null) );
    }*/
}
