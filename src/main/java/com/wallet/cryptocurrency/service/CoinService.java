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
    }

    public Coin getTokenBySymbolFromApi(String symbol, BigDecimal quantity, Wallet walletList) {

        String apiUrl = COINGECKO_API_BASE_URL + "/coins/markets?vs_currency=usd&ids=" + symbol;
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

            coinRepository.save(coin);

            return coin;
        }

        return null;
    }
}
