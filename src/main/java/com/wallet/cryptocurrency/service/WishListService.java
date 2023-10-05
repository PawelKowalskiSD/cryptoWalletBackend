package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.WishList;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.exceptions.WishListNotFoundException;
import com.wallet.cryptocurrency.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WishListService {
    private final WishListRepository wishListRepository;

    public WishList findByWishListIdAndUserId(Long wishListId, Long userId) throws WishListNotFoundException, UserNotFoundException {
        try {
            return wishListRepository.findByWishListIdAndUserUserId(wishListId, userId)
                    .orElseThrow((WishListNotFoundException::new));
        }catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException();
        }
    }

    public void addWishListToUserAccount(User user, WishList wishList) {
        wishList.setUser(user);
        wishListRepository.save(wishList);
    }

    public List<WishList> findWishListByAllUserId(Long id) {
        return wishListRepository.findAllByUser_UserId(id);
    }

    public void deleteWishList(Long wishListId){
        wishListRepository.deleteById(wishListId);
    }
}
