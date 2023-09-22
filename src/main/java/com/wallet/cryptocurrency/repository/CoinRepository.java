package com.wallet.cryptocurrency.repository;

import com.wallet.cryptocurrency.entity.Coin;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface CoinRepository extends CrudRepository<Coin, String> {
    @Override
    List<Coin>findAll();
    void deleteByCoinId(Long coinId);

    Coin findCoinsByCoinName(String name);
}
