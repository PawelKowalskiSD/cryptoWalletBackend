package com.wallet.cryptocurrency.facade;

import com.wallet.cryptocurrency.dto.DashboardDto;
import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.entity.WishList;
import com.wallet.cryptocurrency.mapper.WalletMapper;
import com.wallet.cryptocurrency.mapper.WishListMapper;
import com.wallet.cryptocurrency.service.WalletService;
import com.wallet.cryptocurrency.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DashboardFacade {

    private final WalletService walletService;
    private final WishListService wishListService;
    private final WalletMapper walletMapper;
    private final WishListMapper wishListMapper;

    public List<DashboardDto> fetchDashboard() {
        List<Wallet> wallets = walletService.findAllWallet();
        List<WishList> wishLists = wishListService.findAllWishLists();

        List<DashboardDto> dashboardDtoList = new ArrayList<>();

        dashboardDtoList.add(new DashboardDto(walletMapper.mapToWalletsDto(wallets), wishListMapper.mapToWishListsDto(wishLists)));

        return dashboardDtoList;
    }
}
