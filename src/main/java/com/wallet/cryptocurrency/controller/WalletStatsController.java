package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.dto.WalletStatsDto;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/walletStats")
@RestController
public class WalletStatsController {

    @GetMapping(value = "/{walletStatsId}")
    public WalletStatsDto getWalletStats(@PathVariable Long walletStatsId) {
        return new WalletStatsDto();
    }
}
