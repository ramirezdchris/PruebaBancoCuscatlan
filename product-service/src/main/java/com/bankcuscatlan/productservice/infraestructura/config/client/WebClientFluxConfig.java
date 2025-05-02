package com.bankcuscatlan.productservice.infraestructura.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientFluxConfig {

    @Value("${fake-api.host}")
    private String urlBase;

    private static String uriConsultada;

    @Bean
    public WebClient webClient(){
        return WebClient.builder().baseUrl(urlBase).filter(logRequest()).build();
    }

    private ExchangeFilterFunction logRequest(){
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            uriConsultada = clientRequest.url().toString();
            return Mono.just(clientRequest);
        });
    }

    public static String getUriConsultada(){
        return uriConsultada;
    }
}
