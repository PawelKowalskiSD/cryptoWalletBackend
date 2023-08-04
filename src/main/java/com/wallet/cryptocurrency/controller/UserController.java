package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.dto.UserDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.mapper.UserMapper;
import com.wallet.cryptocurrency.service.UserService;
import com.wallet.cryptocurrency.service.VerifyTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RequestMapping("/v1/cryptoWallet")
@Controller
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    private final VerifyTokenService verifyTokenService;

    @GetMapping(value = "/dashboard")
    public ResponseEntity<Void> getUserAccount() {
        return ResponseEntity.ok().build();
    }


    @PostMapping(value = "/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto) throws Exception {
        User user = userMapper.mapToUser(userDto);
        userService.createUser(user);
        verifyTokenService.createAndSaveToken(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Void> logIn(@RequestBody UserDto userDto) throws Exception {
        User user = userMapper.mapToUser(userDto);
        userService.findUserAccountByUsername(user.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/verify")
    public ModelAndView verifyAccount(@RequestParam String token) {
        System.out.println("input");
        verifyTokenService.verifyToken(token);
        return new ModelAndView("redirect:/login");
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logOut() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/editAccount/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> editAccount(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) throws UserNotFoundException {
        User user = userService.getUserAccount(userId);
        userService.saveUserAccount(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long userId) {
        userService.deleteUserAccountById(userId);
        return ResponseEntity.ok().build();
    }
}

