package com.wallet.cryptocurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishListDto {
    private Long wishListId;
    private String wishListName;
    private Long userId;

    public WishListDto(Long wishListId, String wishListName) {
        this.wishListId = wishListId;
        this.wishListName = wishListName;
    }
}
