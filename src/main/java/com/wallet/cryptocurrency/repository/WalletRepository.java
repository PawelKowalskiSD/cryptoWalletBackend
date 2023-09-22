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
    void deleteWalletByWalletIdAndUser_UserId(Long walletId, Long userId);

    List<Wallet> findAllByUser_UserId(Long id);

    Optional<Wallet> findWalletByWalletIdAndUser_UserId(Long walletId, Long userId);

}
