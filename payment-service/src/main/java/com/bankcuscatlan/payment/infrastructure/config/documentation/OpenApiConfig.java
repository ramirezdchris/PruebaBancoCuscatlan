package com.bankcuscatlan.payment.infrastructure.config.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        try {
            String openApiContent = Files.readString(
                    Paths.get(ClassLoader.getSystemResource("documentation/open-api.yaml").toURI()),
                    StandardCharsets.UTF_8
            );
            return new OpenAPIV3Parser().readContents(openApiContent, null, null).getOpenAPI();
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo OpenAPI", e);
        }
    }
}