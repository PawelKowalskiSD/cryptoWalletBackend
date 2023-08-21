package com.wallet.cryptocurrency.repository;

import com.wallet.cryptocurrency.entity.WishList;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface WishListRepository extends CrudRepository<WishList, Long> {

    @Override
    List<WishList> findAll();
    @Override
    Optional<WishList> findById(Long id);
}
