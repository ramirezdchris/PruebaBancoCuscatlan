package com.bankcuscatlan.productservice.infraestructura.exceptions;

import lombok.RequiredArgsConstructor;


public class ProductServiceUnavailableException extends RuntimeException {

    public ProductServiceUnavailableException() {
    }

    public ProductServiceUnavailableException(String message) {
        super(message);
    }

    public ProductServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductServiceUnavailableException(Throwable cause) {
        super(cause);
    }

    public ProductServiceUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
