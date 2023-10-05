package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.dto.AddCoinToWishlistDto;
import com.wallet.cryptocurrency.dto.CreateCoinDataDto;
import com.wallet.cryptocurrency.dto.SellCoinDataDto;
import com.wallet.cryptocurrency.entity.Coin;
import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CoinService {
    private static final Logger logger = LoggerFactory.getLogger(CoinService.class);
    private static final String COIN_GECKO_API_BASE_URL = "https://api.coingecko.com/api/v3";
    private final CoinRepository coinRepository;

    public void getCoinByNameFromApi(String name, BigDecimal quantity, Wallet walletList) {

        String apiUrl = COIN_GECKO_API_BASE_URL + "/coins/markets?vs_currency=usd&ids=" + name;
        RestTemplate restTemplate = new RestTemplate();
        CreateCoinDataDto[] tokenData = restTemplate.getForObject(apiUrl, CreateCoinDataDto[].class);
        Coin coin = new Coin();
        if (tokenData != null && tokenData.length > 0) {

            CreateCoinDataDto createCoinDataDto = tokenData[0];
            String coinId = createCoinDataDto.getCoinId();
            Coin existingCoin = coinRepository.findCoinsByCoinName(coinId);
            if (existingCoin != null && existingCoin.getInvestedFunds() != null) {
                existingCoin.setAveragePurchasePrice(getDivide(quantity, createCoinDataDto, existingCoin));
                existingCoin.setInvestedFunds(getAddQuantity(existingCoin.getInvestedFunds(), quantity.multiply(createCoinDataDto.getCurrentPrice())));
                existingCoin.setQuantity(getAddQuantity(existingCoin.getQuantity(), quantity));
                existingCoin.setTotalSupply(createCoinDataDto.getTotalSupply());
                getParameterUpdate(createCoinDataDto, existingCoin);

                coinRepository.save(existingCoin);
            } else {

                coin.setCoinName(createCoinDataDto.getCoinName());
                coin.setSymbol(createCoinDataDto.getSymbol());
                coin.setQuantity(quantity.setScale(5, RoundingMode.HALF_DOWN));
                coin.setAveragePurchasePrice(createCoinDataDto.getCurrentPrice().setScale(5, RoundingMode.HALF_DOWN));
                coin.setTotalSupply(createCoinDataDto.getTotalSupply());
                coin.setInvestedFunds(quantity.multiply(createCoinDataDto.getCurrentPrice()));

                getParameterUpdate(createCoinDataDto, coin);
                walletList.getCoinList().add(coin);
                coinRepository.save(coin);
            }
        }
    }

    @NotNull
    private static BigDecimal getAddQuantity(BigDecimal existingCoin, BigDecimal quantity) {
        return existingCoin.add(quantity);
    }

    private static BigDecimal getDivide(BigDecimal quantity, CreateCoinDataDto createCoinDataDto, Coin existingCoin) {
        BigDecimal resultQuantity = getAddQuantity(quantity, existingCoin.getQuantity());
        System.out.println(resultQuantity);
        BigDecimal sumNewFunds = quantity.multiply(createCoinDataDto.getCurrentPrice());
        System.out.println(sumNewFunds);
        System.out.println(existingCoin.getInvestedFunds());
        BigDecimal summary = getAddQuantity(sumNewFunds, existingCoin.getInvestedFunds());
        System.out.println(summary);
        BigDecimal results = summary.divide(resultQuantity, RoundingMode.HALF_DOWN);
        System.out.println(results);
        return results;
    }

    public void refreshStatistics(Long walletId) {
        List<Coin> walletCoins = coinRepository.findAll();
        if (walletCoins.isEmpty()) {
            return;
        }
        for (Coin coins : walletCoins) {
            String txt = coins.getCoinName();
            String apiUrl = COIN_GECKO_API_BASE_URL + "/coins/markets?vs_currency=usd&ids=" + txt;

            RestTemplate restTemplate = new RestTemplate();

            try {
                CreateCoinDataDto[] tokenData = restTemplate.getForObject(apiUrl, CreateCoinDataDto[].class);

                if (tokenData != null) {
                    for (CreateCoinDataDto createCoinDataDto : tokenData) {
                        String coinId = createCoinDataDto.getCoinId();
                        Coin coin = coinRepository.findCoinsByCoinName(coinId);

                        if (coin != null) {
                            getParameterUpdate(createCoinDataDto, coin);
                            coinRepository.save(coin);
                        } else {
                            logger.warn("Coin with ID {} not found in the database.", coinId);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Error refreshing coin statistics for wallet {}: {}", walletId, e.getMessage());
                // Handle the error as needed, e.g., log it or throw a custom exception.
            }
        }
    }

    private static void getParameterUpdate(CreateCoinDataDto createCoinDataDto, Coin coin) {
        coin.setMarketCap(createCoinDataDto.getMarketCap());
        coin.setCurrentPrice(createCoinDataDto.getCurrentPrice());
        coin.setMarketCapRank(createCoinDataDto.getMarketCapRank());
        coin.setHigh24h(createCoinDataDto.getHigh24h().setScale(5, RoundingMode.HALF_DOWN));
        coin.setLow24h(createCoinDataDto.getLow24h().setScale(5, RoundingMode.HALF_DOWN));
        coin.setCirculatingSupply(createCoinDataDto.getCirculatingSupply());
    }

    public void addCoinToWishList(String name, BigDecimal quantity, Long wishListId, AddCoinToWishlistDto coinToWishlistDto) {
        String apiUrl = COIN_GECKO_API_BASE_URL + "/coins/markets?vs_currency=usd&ids=" + name;
        RestTemplate restTemplate = new RestTemplate();
        AddCoinToWishlistDto[] tokenData = restTemplate.getForObject(apiUrl, AddCoinToWishlistDto[].class);
        Coin coin = new Coin();
        if (tokenData != null && tokenData.length > 0) {
            AddCoinToWishlistDto addCoinToWishlistDto = tokenData[0];
            String coinName = addCoinToWishlistDto.getCoinName();
            Coin existingCoin = coinRepository.findCoinsByCoinName(coinName);
            if (existingCoin != null) {
                existingCoin.setQuantity(getAddQuantity(quantity, existingCoin.getQuantity()));

                if (addCoinToWishlistDto.getPurchaseWishPrice() != null) {
                    existingCoin.setPurchaseWishPrice(coinToWishlistDto.getPurchaseWishPrice().setScale(5, RoundingMode.HALF_DOWN));
                }
                setWishListsStatistics(addCoinToWishlistDto, existingCoin);

                coinRepository.save(existingCoin);
            } else {

                coin.setCoinName(addCoinToWishlistDto.getCoinName());
                coin.setQuantity(quantity);
                coin.setPurchaseWishPrice(coinToWishlistDto.getPurchaseWishPrice().setScale(5, RoundingMode.HALF_DOWN));
                coin.setSymbol(addCoinToWishlistDto.getSymbol());
                setWishListsStatistics(addCoinToWishlistDto, coin);

//               wishList.getCoinWishList().add(coin);
                coinRepository.save(coin);
            }
        }
    }

    private static void setWishListsStatistics(AddCoinToWishlistDto addCoinToWishlistDto, Coin existingCoin) {
        existingCoin.setTotalSupply(addCoinToWishlistDto.getTotalSupply());
        existingCoin.setCurrentPrice(addCoinToWishlistDto.getCurrentPrice());
        existingCoin.setCirculatingSupply(addCoinToWishlistDto.getCirculatingSupply());
        existingCoin.setLow24h(addCoinToWishlistDto.getLow24h());
        existingCoin.setHigh24h(addCoinToWishlistDto.getHigh24h());
        existingCoin.setMarketCap(addCoinToWishlistDto.getMarketCap());
        existingCoin.setMarketCapRank(addCoinToWishlistDto.getMarketCapRank());
    }

    public void sellCoins(SellCoinDataDto sellCoinDataDto) {
        String name = sellCoinDataDto.getCoinName();
        BigDecimal quantity = sellCoinDataDto.getQuantity().setScale(5, RoundingMode.HALF_DOWN);
        try {
            String apiUrl = COIN_GECKO_API_BASE_URL + "/coins/markets?vs_currency=usd&ids=" + name;
            RestTemplate restTemplate = new RestTemplate();
            SellCoinDataDto[] tokenData = restTemplate.getForObject(apiUrl, SellCoinDataDto[].class);
            if (tokenData != null && tokenData.length > 0) {
                SellCoinDataDto coinDataDto = tokenData[0];

                Coin coin = coinRepository.findCoinsByCoinName(name);
                coin.setQuantity(coin.getQuantity().subtract(quantity));
                coin.setInvestedFunds(coin.getInvestedFunds().subtract(quantity.multiply(coinDataDto.getCurrentPrice())));
                if (coin.getAverageSalePrice() == null) {
                    coin.setAverageSalePrice(quantity.multiply(coinDataDto.getCurrentPrice()).divide(quantity, RoundingMode.HALF_DOWN));
                } else {
                    coin.setAverageSalePrice(getAddQuantity((quantity.multiply(coinDataDto.getCurrentPrice()).divide(quantity, RoundingMode.HALF_DOWN)), coin.getAverageSalePrice()).divide(new BigDecimal(2), RoundingMode.HALF_DOWN));
                }
                coin.setMarketCap(coinDataDto.getMarketCap());
                coin.setCurrentPrice(coinDataDto.getCurrentPrice());
                coin.setMarketCapRank(coinDataDto.getMarketCapRank());
                coin.setHigh24h(coinDataDto.getHigh24h().setScale(5, RoundingMode.HALF_DOWN));
                coin.setLow24h(coinDataDto.getLow24h().setScale(5, RoundingMode.HALF_DOWN));
                coin.setCirculatingSupply(coinDataDto.getCirculatingSupply());
                coinRepository.save(coin);
            }
        } catch (Exception e) {

        }
    }

    public void reductionAmountOfCoinOnTheWishList(AddCoinToWishlistDto addCoinToWishlistDto) {
        String coinName = addCoinToWishlistDto.getCoinName();
        BigDecimal quantity = addCoinToWishlistDto.getQuantity().setScale(5, RoundingMode.HALF_DOWN);

        String apiUrl = COIN_GECKO_API_BASE_URL + "/coins/markets?vs_currency=usd&ids=" + coinName.toLowerCase();
        RestTemplate restTemplate = new RestTemplate();
        AddCoinToWishlistDto[] tokenData = restTemplate.getForObject(apiUrl, AddCoinToWishlistDto[].class);
        if (tokenData != null && tokenData.length > 0) {
            AddCoinToWishlistDto coinToWishlistDto = tokenData[0];

            Coin coin = coinRepository.findCoinsByCoinName(coinName);
            if (coin != null) {
                if (coin.getQuantity().compareTo(quantity) > 0) {
                    coin.setQuantity(coin.getQuantity().subtract(quantity));
                    if (addCoinToWishlistDto.getPurchaseWishPrice() != null) {
                        coin.setPurchaseWishPrice(addCoinToWishlistDto.getPurchaseWishPrice());
                    }
                    coin.setMarketCap(coinToWishlistDto.getMarketCap());
                    coin.setCurrentPrice(coinToWishlistDto.getCurrentPrice());
                    coin.setMarketCapRank(coinToWishlistDto.getMarketCapRank());
                    coin.setHigh24h(coinToWishlistDto.getHigh24h().setScale(5, RoundingMode.HALF_DOWN));
                    coin.setLow24h(coinToWishlistDto.getLow24h().setScale(5, RoundingMode.HALF_DOWN));
                    coin.setCirculatingSupply(coinToWishlistDto.getCirculatingSupply());

                    coinRepository.save(coin);

                } else if (coin.getQuantity().compareTo(quantity) == 0)
                    coinRepository.deleteByCoinId(coin.getCoinId());
            }
        }
    }
}
