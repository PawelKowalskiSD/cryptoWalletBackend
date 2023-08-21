package com.wallet.cryptocurrency.mapper;

import com.wallet.cryptocurrency.dto.WishListDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.WishList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishListMapper {

    public List<WishList> mapToWishLists(final List<WishListDto> wishListDto) {
        return wishListDto.stream()
                .map(wishList -> new WishList(wishList.getWishListId(), wishList.getWishListName(), new User(wishList.getUserId())))
                .collect(Collectors.toList());
    }

    public List<WishListDto> mapToWishListsDto(final List<WishList> wishLists) {
        return wishLists.stream()
                .map(wishList -> new WishListDto(wishList.getWishListId(), wishList.getWishListName(), wishList.getUser().getUserId()))
                .collect(Collectors.toList());
    }

    public WishList mapToWishlist(final WishListDto wishListDto) {
        return new WishList(
                wishListDto.getWishListId(),
                wishListDto.getWishListName()
        );
    }

    public WishListDto mapToWishListDto(final WishList wishList) {
        return new WishListDto(
                wishList.getWishListId(),
                wishList.getWishListName(),
                wishList.getUser().getUserId()
        );
    }
}
