package com.bankcuscatlan.productservice.infraestructura.config.client;

import com.bankcuscatlan.productservice.infraestructura.exceptions.ProductNotFoundException;
import com.bankcuscatlan.productservice.infraestructura.exceptions.ProductServiceUnavailableException;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.net.ConnectException;

@Component
public class FakeApiClientFactory implements FallbackFactory<FakeApiClient> {
    @Override
    public FakeApiClient create(Throwable cause) {
        System.out.println("Aqui esta la causa " +cause.getMessage());
        return id -> {
            if(cause instanceof FeignException.NotFound) {
                throw new ProductNotFoundException("Product: " +id +" not found.");
            } else if (cause instanceof RetryableException) {
                throw new ProductServiceUnavailableException("Service FakeApi Unavailable.");
            } else if (cause instanceof ConnectException) {
                throw new ProductServiceUnavailableException("Service FakeApi Unavailable.");
            }
            throw new ProductServiceUnavailableException("Service FakeApi Unavailable.");
        };
    }
}
