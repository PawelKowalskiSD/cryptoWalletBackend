package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.domain.User;
import com.wallet.cryptocurrency.dto.UserDto;
import com.wallet.cryptocurrency.exceptions.AccountExistsException;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.mapper.UserMapper;
import com.wallet.cryptocurrency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/v1/cryptoWallet")
@RestController
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping(value = "/{userId}")
    public UserDto getUserAccount(@PathVariable Long userId) {
        return new UserDto();
    }

    @PostMapping(value = "/login")
    public UserDto logIn(@RequestBody UserDto userDto) {
        return new UserDto("pass123", "mak@wp.pl", true, false, false);
    }

    @PostMapping(value = "/logout")
    public UserDto logout(@RequestBody UserDto userDto) {
        return new UserDto(false);
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<UserDto> registration(@RequestBody UserDto userDto) throws AccountExistsException{
        User user = userMapper.mapToUser(userDto);
        Optional<User> userOptional = userService.findUserAccountByMailAddressee(user.getMailAddressee());
        if (userOptional.isPresent()) {
            throw new AccountExistsException();
        } else {
            user.setRegistration(true);
            userService.saveUserAccount(user);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/editAccount")
    public UserDto editAccount(@RequestBody UserDto userDto) {
        return new UserDto("pawel", "smith", "jonas", "pas", "mak@wp.pl", false, false, false);
    }

    @DeleteMapping(value = "{userId}")
    public void deleteAccount(@PathVariable Long userId) {
    }
}
