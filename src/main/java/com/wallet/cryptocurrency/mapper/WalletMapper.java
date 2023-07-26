package com.wallet.cryptocurrency.mapper;

import com.wallet.cryptocurrency.dto.WalletDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class WalletMapper {

    private final UserService userService;

    public Wallet mapToWallet(final WalletDto walletDto) throws UserNotFoundException {
        User user = userService.getUserAccount(walletDto.getUserId());
        return new Wallet(
                walletDto.getWalletName(),
                user
        );
    }
}
