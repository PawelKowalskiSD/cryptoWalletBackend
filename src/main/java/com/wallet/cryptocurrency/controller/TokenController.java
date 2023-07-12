package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.dto.TokenDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/token")
@RestController
public class TokenController {

    @GetMapping("/{tokenId}")
    public TokenDto getToken(@PathVariable Long tokenId) {
        return new TokenDto();
    }

    @PostMapping(value = "/addTokenPurchase",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addTokenPurchase(@RequestBody TokenDto tokenDto) {
    }

    @PostMapping(value = "/sellToken", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sellToken(@RequestBody TokenDto tokenDto) {
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TokenDto updateToken(@RequestBody TokenDto tokenDto) {
        return new TokenDto();
    }

    @PostMapping(value = "/addTokenToWishList",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addTokenToWishList(@RequestBody TokenDto tokenDto) {

    }

    @DeleteMapping(value = "/deleteFromWishList/{tokenId}")
    public void deleteTokenFromWishList(@PathVariable Long tokenId) {
    }
}
