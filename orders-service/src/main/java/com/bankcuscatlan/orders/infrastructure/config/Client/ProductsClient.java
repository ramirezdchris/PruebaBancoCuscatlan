package com.bankcuscatlan.orders.infrastructure.config.Client;


import com.bankcuscatlan.orders.infrastructure.exceptions.NotFoundException;
import com.bankcuscatlan.orders.infrastructure.exceptions.ServiceUnavailableException;
import com.bankcuscatlan.orders.model.dto.customer.CustomerDTO;
import com.bankcuscatlan.orders.model.dto.product.ProductDTO;
import com.bankcuscatlan.orders.model.dto.request.RequestProductOrderDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseOrderDetailDTO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ProductsClient {

    private final WebClient.Builder builder;
    private WebClient webClient;

    @Value("${products-api.host}")
    private String host;
    @Value("${products-api.path}")
    private String path;

    public ProductsClient(WebClient.Builder builder) {
        this.builder = builder;
    }

    @PostConstruct
    public void init() {
        this.webClient = builder.baseUrl(host).build();
    }

    public Mono<ResponseDTO<ProductDTO>> getProductById(Long id) {

        return webClient.get()
                .uri(path + "/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                        log.error("Product with ID {} not found", id);
                        return Mono.error(new NotFoundException("Product with ID " + id + " not found"));
                    }
                    log.error("Error search product {}. HTTP code: {}", id, response.statusCode());
                    return Mono.error(new NotFoundException("Product invalid. Code: " + response.statusCode()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error("Product service ServiceUnavailable - CodeError {}", response.statusCode());
                    return Mono.error(new ServiceUnavailableException("Product service ServiceUnavailable"));
                })
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<ProductDTO>>() {})
                .doOnSubscribe(sub -> log.info("Calling product service: {}{}", host, path + "/" + id))
                .doOnNext(response -> log.info("Product found: {}", response));


    }




}
