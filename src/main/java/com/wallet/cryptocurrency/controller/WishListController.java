package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.config.ConfigAuthentication;
import com.wallet.cryptocurrency.dto.WishListDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.WishList;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.exceptions.UserPermissionsException;
import com.wallet.cryptocurrency.exceptions.WishListNotFoundException;
import com.wallet.cryptocurrency.mapper.WishListMapper;
import com.wallet.cryptocurrency.service.UserService;
import com.wallet.cryptocurrency.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/wish-lists")
@RestController
public class WishListController {
    private final WishListMapper wishListMapper;
    private final WishListService wishListService;
    private final UserService userService;
    private final ConfigAuthentication configAuthentication;

    @GetMapping
    public ResponseEntity<List<WishListDto>> getWishLists(Authentication authentication) throws UserPermissionsException {
        Long userId = configAuthentication.getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok().body(wishListMapper.mapToWishListsDto(wishListService.findWishListByAllUserId(userId)));
    }

    @GetMapping(value = "/{wishListId}")
    public ResponseEntity<WishListDto> getWishList(@PathVariable Long wishListId, Authentication authentication) throws UserPermissionsException, UserNotFoundException, WishListNotFoundException {
        Long userId = configAuthentication.getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok().body(wishListMapper.mapToWishListDto(wishListService.findByWishListIdAndUserId(wishListId, userId)));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createWishList(@RequestBody WishListDto wishListDto, Authentication authentication) throws UserNotFoundException, UserPermissionsException {
        Long userId = configAuthentication.getUserIdFromAuthentication(authentication);
        User user = userService.findUserAccountById(userId);
        WishList wishList = wishListMapper.mapToWishlist(wishListDto);
        wishListService.addWishListToUserAccount(user, wishList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{wishListId}")
    public ResponseEntity<Void> deleteWishList(@PathVariable Long wishListId, Authentication authentication) throws UserPermissionsException, UserNotFoundException, WishListNotFoundException {
        Long userId = configAuthentication.getUserIdFromAuthentication(authentication);
        wishListService.findByWishListIdAndUserId(wishListId, userId);
        return ResponseEntity.ok().build();
    }
}
