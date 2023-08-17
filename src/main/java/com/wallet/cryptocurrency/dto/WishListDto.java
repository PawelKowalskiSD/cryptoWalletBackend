package com.wallet.cryptocurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishListDto {
    private Long wishListId;
    private String wishListName;
    private BigDecimal quantity;
    private BigDecimal priceTarget;
    private Long userId;
}
