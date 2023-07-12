package com.wallet.cryptocurrency.repository;

import com.wallet.cryptocurrency.domain.WalletStats;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface WalletStatusRepository extends CrudRepository<WalletStats, Long> {
}
