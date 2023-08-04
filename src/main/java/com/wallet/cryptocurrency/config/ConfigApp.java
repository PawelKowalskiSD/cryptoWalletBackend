package com.wallet.cryptocurrency.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ConfigApp {

    @Value("${localhost.api.endpoint.prod}")
    private String localhostApiEndpoint;
}
