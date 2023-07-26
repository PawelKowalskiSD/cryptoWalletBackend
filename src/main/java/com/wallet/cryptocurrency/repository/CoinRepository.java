package com.wallet.cryptocurrency.repository;

import com.wallet.cryptocurrency.entity.Coin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CoinRepository extends CrudRepository<Coin, String> {
    @Override
    Coin save(Coin coin);

    Optional<Coin> findById(String tokenId);
}
