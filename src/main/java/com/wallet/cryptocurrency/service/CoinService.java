package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.dto.CoinDataDto;
import com.wallet.cryptocurrency.entity.Coin;
import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.repository.CoinRepository;
import com.wallet.cryptocurrency.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CoinService {

    private static final String COINGECKO_API_BASE_URL = "https://api.coingecko.com/api/v3";
    private final CoinRepository coinRepository;

    private final WalletRepository walletRepository;

    private final RestTemplate restTemplate;

    public CoinDataDto getTokenFromApi(String tokenId) {
        String apiUrl = COINGECKO_API_BASE_URL + "/coins/" + tokenId;
        CoinDataDto tokenResponse = restTemplate.getForObject(apiUrl, CoinDataDto.class);
        return tokenResponse;
    }

    public Coin saveCoin(Coin coin) {
        return coinRepository.save(coin);
        // Logika zapisu tokenu do bazy danych (np. za pomocą repozytorium JPA)
    }

    public Coin getTokenBySymbolFromApi(String symbol, BigDecimal quantity, Wallet walletList) {
        // Wykonaj żądanie HTTP do CoinGecko API, aby pobrać informacje o tokenie na podstawie symbolu

        // Przykładowa implementacja żądania HTTP (może wymagać dodatkowych importów)
        String apiUrl = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=" + symbol;
        RestTemplate restTemplate = new RestTemplate();
        CoinDataDto[] tokenData = restTemplate.getForObject(apiUrl, CoinDataDto[].class);

        if (tokenData != null && tokenData.length > 0) {
            CoinDataDto coinDataDto = tokenData[0];

            Coin coin = new Coin();
            coin.setTokenName(coinDataDto.getTokenName());
            coin.setSymbol(coinDataDto.getSymbol());
            coin.setQuantity(quantity.setScale(5, RoundingMode.HALF_DOWN));
            coin.setUrlImages(coinDataDto.getImage());
            walletList.getCoinList().add(coin);
            System.out.println(coin);
            // Ustawienie innych pól w obiekcie tokena na podstawie odpowiedzi z API CoinGecko
            coinRepository.save(coin);

            return coin;
        }

        return null;
    }
}
