package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.config.ConfigAuthentication;
import com.wallet.cryptocurrency.dto.WalletDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.exceptions.UserPermissionsException;
import com.wallet.cryptocurrency.exceptions.WalletNotFoundException;
import com.wallet.cryptocurrency.mapper.WalletMapper;
import com.wallet.cryptocurrency.service.UserService;
import com.wallet.cryptocurrency.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/wallets")
@RestController
public class WalletController {
    private final WalletMapper walletMapper;
    private final WalletService walletService;
    private final UserService userService;

    private final ConfigAuthentication configAuthentication;

    @GetMapping
    public ResponseEntity<List<WalletDto>> getWalletList(Authentication authentication) throws UserPermissionsException {
        Long userId = configAuthentication.getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok().body(walletMapper.mapToWalletsDto(walletService.findWalletsByUserId(userId)));
    }

    @GetMapping(value = "/{walletId}")
    public ResponseEntity<WalletDto> getWallet(@PathVariable Long walletId, Authentication authentication) throws UserPermissionsException, UserNotFoundException, WalletNotFoundException {
        Long userId = configAuthentication.getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok().body(walletMapper.mapToWalletDto(walletService.findByWalletIdAndUserId(walletId, userId)));
    }

    @PostMapping("/create-wallets")
    public ResponseEntity<Void> createWallet(@RequestBody WalletDto walletDto, Authentication authentication) throws UserNotFoundException, UserPermissionsException {
        Long userId = configAuthentication.getUserIdFromAuthentication(authentication);
        User user = userService.findUserAccountById(userId);
        Wallet wallet = walletMapper.mapToWallet(walletDto);
        walletService.addWalletToUserAccount(user, wallet);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{walletId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletDto> updateWallet(@RequestBody WalletDto walletDto, @PathVariable Long walletId, Authentication authentication) throws UserPermissionsException, UserNotFoundException, WalletNotFoundException {
        Long userId = configAuthentication.getUserIdFromAuthentication(authentication);
        Wallet wallet = walletService.findByWalletIdAndUserId(walletId, userId);
        return ResponseEntity.ok().body(walletMapper.mapToWalletDto(walletService.editWallet(wallet, walletDto)));
    }

    @DeleteMapping(value = "/{walletId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long walletId, Authentication authentication) throws UserPermissionsException {
        Long userId = configAuthentication.getUserIdFromAuthentication(authentication);
        walletService.deleteWalletByIdAndUserId(walletId, userId);
        return ResponseEntity.ok().build();
    }
}
