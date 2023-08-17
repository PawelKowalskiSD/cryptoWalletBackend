package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.dto.WalletDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.exceptions.WalletNotFoundException;
import com.wallet.cryptocurrency.mapper.WalletMapper;
import com.wallet.cryptocurrency.service.UserService;
import com.wallet.cryptocurrency.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@RequestMapping("/wallets")
@RestController
public class WalletController {

    private final WalletMapper walletMapper;
    private final WalletService walletService;

    private final UserService userService;



    @GetMapping
    public List<WalletDto> getWalletList(){
        return new ArrayList<>();
    }

    @GetMapping(value = "/{walletId}")
    public ResponseEntity<WalletDto> getWallet(@PathVariable Long walletId) throws WalletNotFoundException {
        walletService.findWalletById(walletId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create-wallet/{userId}")
    public ResponseEntity<Void> createWallet(@RequestBody WalletDto walletDto, @PathVariable Long userId) throws UserNotFoundException {
        User user = userService.findUserAccountById(userId);
        Wallet wallet = walletMapper.mapToWallet(walletDto);
        user.addWallet(wallet);

        walletService.walletSave(wallet);

        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletDto> updateWallet(@RequestBody WalletDto walletDto) throws UserNotFoundException {
        Wallet wallet = walletMapper.mapToWallet(walletDto);
        walletService.editWallet(wallet);
        walletService.walletSave(wallet);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{walletId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long walletId) {
        walletService.deleteWalletById(walletId);
        return ResponseEntity.ok().build();
    }

}
