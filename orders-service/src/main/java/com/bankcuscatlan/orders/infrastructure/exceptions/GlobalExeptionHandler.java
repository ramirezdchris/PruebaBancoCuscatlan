package com.bankcuscatlan.orders.infrastructure.exceptions;

import com.bankcuscatlan.orders.model.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleNotFound(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(HttpStatus.NOT_FOUND, ex.getMessage(), null) );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleServiceUnavailableException(ServiceUnavailableException ex){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ResponseDTO(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), null) );
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ResponseDTO<String>> handleValidationErrors(WebExchangeBindException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(
                ResponseDTO.<String>builder()
                        .code(HttpStatus.BAD_REQUEST)
                        .message("Validation failed")
                        .data(errors)
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(
                ResponseDTO.<String>builder()
                        .code(HttpStatus.BAD_REQUEST)
                        .message("Validation failed")
                        .data(errors)
                        .build()
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null) );
    }
}
