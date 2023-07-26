package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.coingeko.client.CoinGeckoClient;
import com.wallet.cryptocurrency.dto.CoinDataDto;
import com.wallet.cryptocurrency.dto.CoinDto;
import com.wallet.cryptocurrency.entity.Coin;
import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.mapper.CoinMapper;
import com.wallet.cryptocurrency.service.CoinService;
import com.wallet.cryptocurrency.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/token")
@RestController
public class CoinController {

    private final CoinGeckoClient coinGeckoClient;

    private final CoinService coinService;

    private final CoinMapper coinMapper;

    private final WalletService walletService;

    @GetMapping("/{tokenId}")
    public ResponseEntity<CoinDto> getToken(@PathVariable String tokenId) {
        CoinDto coinDto = coinGeckoClient.searchToken(tokenId);
        if (coinDto != null && coinDto.getCoinDataDto() != null && coinDto.getCoinDataDto().length > 0) {
            return ResponseEntity.ok(coinDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{walletId}/addToken",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addTokenBySymbol(@PathVariable Long walletId, @RequestBody CoinDataDto coinDataDtoDto) {
        try {
            Wallet wallet = walletService.findWalletById(walletId);
            String name = coinDataDtoDto.getTokenName();
            BigDecimal quantity = coinDataDtoDto.getQuantity().setScale(5, RoundingMode.HALF_DOWN);
            Coin coin = coinService.getTokenBySymbolFromApi(name, quantity, wallet);

            if (coin != null) {
                System.out.println(coinDataDtoDto);
                coinService.saveCoin(coin);
                System.out.println(coin);
                return ResponseEntity.ok("Token added successfully");
            } else {
                return ResponseEntity.badRequest().body("Token not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred");
        }
    }

    @PostMapping(value = "/deleteToken", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sellToken(@RequestBody CoinDataDto coinDataDto) {
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public CoinDataDto updateToken(@RequestBody CoinDataDto coinDataDto) {
        return new CoinDataDto();
    }

}
