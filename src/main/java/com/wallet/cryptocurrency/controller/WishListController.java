package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.dto.WishListDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/wish-lists")
@RestController
public class WishListController {

    @GetMapping
    public ResponseEntity<List<WishListDto>> getWishLists() {

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{wishListId}")
    public WishListDto getWishList(@PathVariable Long wishListId) {
        return new WishListDto();
    }

    @PostMapping(value = "/create-wish-list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createWishList(@RequestBody WishListDto wishListDto) {
    }

    @DeleteMapping(value = "{wishListId}")
    public void deleteWishList(@PathVariable Long wishListId){
    }
}
