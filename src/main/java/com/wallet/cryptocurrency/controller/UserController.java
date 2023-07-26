package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.dto.UserDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.mapper.UserMapper;
import com.wallet.cryptocurrency.service.UserService;
import com.wallet.cryptocurrency.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/v1/cryptoWallet")
@Controller
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final UserValidator userValidator;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDto> getUserAccount(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(userMapper.mapToUserDto(userService.getUserAccount(userId)));
    }


    @PostMapping(value = "/registration")
    public ResponseEntity<UserDto> registration(@RequestBody UserDto userDto) throws Exception {
        User user = userMapper.mapToUser(userDto);
        userService.createUser(user);
        userService.saveUserAccount(user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/editAccount/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> editAccount(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) throws UserNotFoundException {
        User user = userService.getUserAccount(userId);

        userValidator.validateUseraddExtraKey(userDto, user);
        userService.saveUserAccount(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long userId) {
        userService.deleteUserAccountById(userId);
        return ResponseEntity.ok().build();
    }
}

