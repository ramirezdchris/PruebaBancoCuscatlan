package com.bankcuscatlan.productservice.service.impl;

import com.bankcuscatlan.productservice.infraestructura.config.client.FakeApiClient;
import com.bankcuscatlan.productservice.infraestructura.config.client.WebClientFluxConfig;
import com.bankcuscatlan.productservice.infraestructura.exceptions.ProductNotFoundException;
import com.bankcuscatlan.productservice.infraestructura.exceptions.ProductServiceUnavailableException;
import com.bankcuscatlan.productservice.model.dto.fakeapi.Product;
import com.bankcuscatlan.productservice.model.dto.product.ResponseDTO;
import com.bankcuscatlan.productservice.service.IProducts;
import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.ConnectException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsImpl implements IProducts {

    private final FakeApiClient fakeApiClient;

    @Override
    @CircuitBreaker(name = "fake-apiproducts")
    @Retry(name = "fake-apiproducts", fallbackMethod = "fallback")
    public ResponseDTO getProductFromApi(Integer id) {
        Product response = fakeApiClient.getProduct(id);
        if(ObjectUtils.isEmpty(response)) {
            throw new ProductNotFoundException("Product: " +id +" not found");
        }
        return new ResponseDTO<>("200", "Success", response);
    }

    public ResponseDTO<Product> fallback(Integer id, Throwable cause) {
        if(cause instanceof FeignException.NotFound) {
            throw new ProductNotFoundException("Product: " +id +" not found.");
        } else if (cause instanceof RetryableException) {
            throw new ProductServiceUnavailableException("Service FakeApi Unavailable.");
        } else if (cause instanceof ConnectException) {
            throw new ProductServiceUnavailableException("Service FakeApi Unavailable.");
        }
        throw new ProductServiceUnavailableException("Service FakeApi Unavailable.");
    }
}
