package com.example.coinbe.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    private final Dotenv dotenv;

    public DataSourceConfig(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://" + dotenv.get("DB_HOST") + ":" + dotenv.get("DB_PORT") + "/" + dotenv.get("DB_NAME"))
                .username(dotenv.get("DB_USER"))
                .password(dotenv.get("DB_PASSWORD"))
                .build();
    }
}
