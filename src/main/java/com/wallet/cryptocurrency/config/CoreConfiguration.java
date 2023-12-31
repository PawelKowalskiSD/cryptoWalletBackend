package com.wallet.cryptocurrency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoreConfiguration {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
