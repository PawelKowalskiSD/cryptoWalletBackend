package com.wallet.cryptocurrency.coingeko.client;

import com.wallet.cryptocurrency.dto.CoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CoinGeckoClient {

    private final RestTemplate restTemplate;

    @Value("${coinGecko.api.endpoint.prod}")
    private String coinGeckoApiEndpoint;

    public CoinDto searchToken(String query) {
        String apiUrl = coinGeckoApiEndpoint + "/search?query=" + query;
        ResponseEntity<CoinDto> response = restTemplate.getForEntity(apiUrl, CoinDto.class);
        return response.getBody();
    }
}
