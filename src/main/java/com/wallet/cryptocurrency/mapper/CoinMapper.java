package com.wallet.cryptocurrency.mapper;

import com.wallet.cryptocurrency.dto.CoinDataDto;
import com.wallet.cryptocurrency.entity.Coin;
import org.springframework.stereotype.Service;

@Service
public class CoinMapper {

    public Coin mapToToken(CoinDataDto coinDataDto) {
        Coin coin = new Coin();
        coin.setCoinName(coinDataDto.getCoinName());
        coin.setSymbol(coinDataDto.getSymbol());
        return coin;
    }
//    public CoinDataDto mapToTokenDto(final Coin coin) {
//        return new CoinDataDto(
//                coin.getTokenName(),
//                coin.getSymbol(),
//                coin.getQuantity()
//        );
//    }
}
