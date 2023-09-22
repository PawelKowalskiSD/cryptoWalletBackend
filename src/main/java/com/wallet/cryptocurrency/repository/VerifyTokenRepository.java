package com.wallet.cryptocurrency.repository;

import com.wallet.cryptocurrency.entity.VerifyToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyTokenRepository extends CrudRepository<VerifyToken, Long> {
    VerifyToken findByValue(String value);

}
