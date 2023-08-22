package com.wallet.cryptocurrency.repository;

import com.wallet.cryptocurrency.entity.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {
    @Override
    Optional<Wallet> findById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    List<Wallet> findAll();

    List<Wallet> findAllByUser_UserId(Long id);
}
