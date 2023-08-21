package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.dto.WishListDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.WishList;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.mapper.WishListMapper;
import com.wallet.cryptocurrency.service.UserService;
import com.wallet.cryptocurrency.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RequestMapping("/wish-lists")
@RestController
public class WishListController {

    private final WishListMapper wishListMapper;
    private final WishListService wishListService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<WishListDto>> getWishLists() {
        return ResponseEntity.ok().body(wishListMapper.mapToWishListsDto(wishListService.findAllWishLists()));
    }

    @GetMapping(value = "/{wishListId}")
    public ResponseEntity<WishListDto> getWishList(@PathVariable Long wishListId) throws Exception {
        return ResponseEntity.ok().body(wishListMapper.mapToWishListDto(wishListService.findByWishListId(wishListId)));
    }

    @PostMapping(value = "/create/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createWishList(@RequestBody WishListDto wishListDto, @PathVariable Long userId) throws UserNotFoundException {
        User user = userService.findUserAccountById(userId);
        WishList wishList = wishListMapper.mapToWishlist(wishListDto);
        wishListService.addWishListToUserAccount(user, wishList);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping(value = "{wishListId}")
    public ResponseEntity<Void> deleteWishList(@PathVariable Long wishListId) throws Exception {
        wishListService.findByWishListId(wishListId);
        return ResponseEntity.ok().build();
    }
}
