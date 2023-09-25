package com.wallet.cryptocurrency.facade;

import com.wallet.cryptocurrency.config.ConfigAuthentication;
import com.wallet.cryptocurrency.dto.DashboardDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.entity.WishList;
import com.wallet.cryptocurrency.exceptions.UserPermissionsException;
import com.wallet.cryptocurrency.mapper.WalletMapper;
import com.wallet.cryptocurrency.mapper.WishListMapper;
import com.wallet.cryptocurrency.service.WalletService;
import com.wallet.cryptocurrency.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final ConfigAuthentication configAuthentication;

    public List<DashboardDto> fetchDashboard(Authentication authentication) throws UserPermissionsException {
        Long userId = configAuthentication.getUserIdFromAuthentication(authentication);
        List<Wallet> wallets = walletService.findWalletsByUserId(userId);
        List<WishList> wishLists = wishListService.findWishListByAllUserId(userId);

        List<DashboardDto> dashboardDtoList = new ArrayList<>();

        dashboardDtoList.add(new DashboardDto(walletMapper.mapToWalletsDto(wallets), wishListMapper.mapToWishListsDto(wishLists)));

        return dashboardDtoList;
    }
}
