package com.wallet.cryptocurrency.repository;

import com.wallet.cryptocurrency.domain.WishList;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface WishListRepository extends CrudRepository<WishList, Long> {
}
