package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.coingeko.client.CoinGeckoClient;
import com.wallet.cryptocurrency.dto.AddCoinToWishlistDto;
import com.wallet.cryptocurrency.dto.CoinDto;
import com.wallet.cryptocurrency.dto.CreateCoinDataDto;
import com.wallet.cryptocurrency.dto.SellCoinDataDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.entity.WishList;
import com.wallet.cryptocurrency.service.CoinService;
import com.wallet.cryptocurrency.service.WalletService;
import com.wallet.cryptocurrency.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@RequestMapping("/coins")
@RestController
public class CoinController {
    private final CoinGeckoClient coinGeckoClient;
    private final CoinService coinService;
    private final WalletService walletService;
    private final WishListService wishListService;

    @GetMapping(value = "/{coin}")
    public ResponseEntity<CoinDto> searchCoin(@PathVariable String coin) {
        CoinDto coinDto = coinGeckoClient.searchToken(coin);
        if (coinDto.getCoinDataDto().length > 0) {
            return ResponseEntity.ok(coinDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{walletId}/buy-coins", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateCoinDataDto> buyACoins(@PathVariable Long walletId, @RequestBody CreateCoinDataDto createCoinDataDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getUserId();

        Wallet wallet = walletService.findByWalletIdAndUserId(walletId, userId);
        String name = createCoinDataDto.getCoinName();
        BigDecimal quantity = createCoinDataDto.getQuantity().setScale(5, RoundingMode.HALF_DOWN);
        coinService.getCoinByNameFromApi(name, quantity, wallet);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{wishListId}/add-coins-to-wish-lists")
    public ResponseEntity<AddCoinToWishlistDto> addCoinToWishList(@PathVariable Long wishListId, @RequestBody AddCoinToWishlistDto addCoinToWishlistDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getUserId();

        WishList wishList = wishListService.findByWishListIdAndUserId(wishListId, userId);
        BigDecimal quantity = addCoinToWishlistDto.getQuantity().setScale(5, RoundingMode.HALF_DOWN);
        String name = addCoinToWishlistDto.getCoinName();
        coinService.addCoinToWishList(name, quantity, wishList, addCoinToWishlistDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{walletId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> refreshTokenStatistics(@PathVariable Long walletId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getUserId();

        walletService.findByWalletIdAndUserId(walletId, userId);
        coinService.refreshStatistics(walletId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/wallet/{walletId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SellCoinDataDto> sellCoinFromWallet(@PathVariable Long walletId, @RequestBody SellCoinDataDto sellCoinDataDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getUserId();

        walletService.findByWalletIdAndUserId(walletId, userId);
        coinService.sellCoins(sellCoinDataDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/delete-coins-from-wish-list/{wishListId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> reductionOfTheAmountOfCoinOnTheWishList(@RequestBody AddCoinToWishlistDto addCoinToWishlistDto, @PathVariable Long wishListId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getUserId();

        wishListService.findByWishListIdAndUserId(wishListId, userId);
        coinService.reductionAmountOfCoinOnTheWishList(addCoinToWishlistDto);
        return ResponseEntity.ok().build();
    }
}
