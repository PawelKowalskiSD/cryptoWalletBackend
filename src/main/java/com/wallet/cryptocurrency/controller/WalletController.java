package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.dto.WalletDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.mapper.WalletMapper;
import com.wallet.cryptocurrency.service.UserService;
import com.wallet.cryptocurrency.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/wallets")
@RestController
public class WalletController {
    private final WalletMapper walletMapper;
    private final WalletService walletService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<WalletDto>> getWalletList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        return ResponseEntity.ok().body(walletMapper.mapToWalletsDto(walletService.findWalletsByUserId(userId)));
    }

    @GetMapping(value = "/{walletId}")
    public ResponseEntity<WalletDto> getWallet(@PathVariable Long walletId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        return ResponseEntity.ok().body(walletMapper.mapToWalletDto(walletService.findByWalletIdAndUserId(walletId, userId)));
    }

    @PostMapping("/create-wallets")
    public ResponseEntity<Void> createWallet(@RequestBody WalletDto walletDto) throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        User user = userService.findUserAccountById(userId);
        Wallet wallet = walletMapper.mapToWallet(walletDto);
        walletService.addWalletToUserAccount(user, wallet);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{walletId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletDto> updateWallet(@RequestBody WalletDto walletDto, @PathVariable Long walletId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        Wallet wallet = walletService.findByWalletIdAndUserId(walletId, userId);
        return ResponseEntity.ok().body(walletMapper.mapToWalletDto(walletService.editWallet(wallet, walletDto)));
    }

    @DeleteMapping(value = "/{walletId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long walletId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        walletService.deleteWalletByIdAndUserId(walletId, userId);
        return ResponseEntity.ok().build();
    }
}
