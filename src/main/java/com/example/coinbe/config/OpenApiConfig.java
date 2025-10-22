package com.example.coinbe.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI viegymAppOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("COIN API Documentation")
                        .description("""
                                📘 REST API cho ứng dụng huy.nt.geekup.vn"
                                """)
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Nguyễn Thành Huy")
                                .email("huy.nt.geekup.vn")
                                .url("huy.nt.geekup.vn"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org"))
                );
    }
}