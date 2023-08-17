package com.wallet.cryptocurrency.mapper;

import com.wallet.cryptocurrency.dto.WalletDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WalletMapper {

    private final UserService userService;

    public List<Wallet> mapToWallets(final List<WalletDto> walletDto) {
        return walletDto.stream()
                .map(wallet -> new Wallet(wallet.getWalletId(), wallet.getWalletName(), new User(wallet.getUserId())))
                .collect(Collectors.toList());
    }

    public List<WalletDto> mapToWalletsDto(final List<Wallet> wallets) {
        return wallets.stream()
                .map((wallet -> new WalletDto(wallet.getWalletId(), wallet.getWalletName(), wallet.getWalletId())))
                .collect(Collectors.toList());
    }

    public Wallet mapToWallet(final WalletDto walletDto) throws UserNotFoundException {
        return new Wallet(walletDto.getWalletId(),
                walletDto.getWalletName()
                );
    }
}
