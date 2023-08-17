package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.entity.WishList;
import com.wallet.cryptocurrency.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public List<WishList> findAllWishLists() {
        return wishListRepository.findAll();
    }
}
