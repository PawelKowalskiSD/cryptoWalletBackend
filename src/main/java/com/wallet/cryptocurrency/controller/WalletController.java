package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.dto.WalletDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/v1/wallet")
@RestController
public class WalletController {

    @GetMapping
    public List<WalletDto> getWalletList(){
        return new ArrayList<>();
    }

    @GetMapping(value = "/{walletId}")
    public WalletDto getWallet(@PathVariable Long walletId) {
        return new WalletDto();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createWallet(@RequestBody WalletDto walletDto) {

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public WalletDto updateWallet(@RequestBody WalletDto walletDto) {
        return new WalletDto();
    }

    @DeleteMapping(value = "/{walletId}")
    public void deleteWallet(@PathVariable Long walletId) {
    }

    @DeleteMapping(value = "/deleteAllWallet")
    public void deleteAllWallet() {

    }
}
