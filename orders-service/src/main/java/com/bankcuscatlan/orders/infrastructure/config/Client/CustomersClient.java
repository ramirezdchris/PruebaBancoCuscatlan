package com.bankcuscatlan.orders.infrastructure.config.Client;

import com.bankcuscatlan.orders.infrastructure.exceptions.NotFoundException;
import com.bankcuscatlan.orders.infrastructure.exceptions.ServiceUnavailableException;
import com.bankcuscatlan.orders.model.dto.customer.CustomerDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseDTO;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomersClient {
    private final WebClient.Builder builder;
    private WebClient webClient;


    @Value("${customers-api.host}")
    private String host;
    @Value("${customers-api.path}")
    private String path;

    public CustomersClient(WebClient.Builder builder) {
        this.builder = builder;
    }

    @PostConstruct
    public void init() {
        this.webClient = builder.baseUrl(host).build();
    }

    public Mono<ResponseDTO> getCustomerById(Long id) {

        return webClient.get()
                .uri(path + "/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                        log.error("Customer with ID {} not found", id);
                        return Mono.error(new NotFoundException("Customer with ID " + id + " not found"));
                    }
                    log.error("Error search client {}. HTTP code: {}", id, response.statusCode());
                    return Mono.error(new Exception("Customer invalid. Code: " + response.statusCode()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error("Customer service ServiceUnavailable - CodeError {}", response.statusCode());
                    return Mono.error(new ServiceUnavailableException("Customer service ServiceUnavailable"));
                })
                .bodyToMono(ResponseDTO.class)
                .doOnSubscribe(sub -> log.info("Calling customer service: {}{}", host, path + "/" + id))
                .doOnNext(response -> log.info("Customer found: {}", response.getData()))
                 ;

                /*.onErrorResume(e -> {
                    log.error("Failed to fetch customer", e);
                    return Mono.error(new ServiceUnavailableException("Customer service Service Unavailable"));
                });*/
                //.onErrorResume(throwable -> Mono.error(new NotFoundException("Customer " +id +" Not Found")) );

    }

}
