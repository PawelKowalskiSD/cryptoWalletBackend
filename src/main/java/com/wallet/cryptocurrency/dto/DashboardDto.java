package com.wallet.cryptocurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class DashboardDto {
    private List<WalletDto> walletList;
    private List<WishListDto> wishListList;
}
