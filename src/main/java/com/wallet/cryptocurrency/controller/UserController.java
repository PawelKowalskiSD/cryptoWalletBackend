package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.dto.UserDto;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/cryptoWallet")
@RestController
public class UserController {

    @PostMapping(value = "/login")
    public UserDto logIn(@RequestBody UserDto userDto) {
        return new UserDto("pass123", "mak@wp.pl", true, false, false);
    }

    @PostMapping(value = "/logout")
    public UserDto logout(@RequestBody UserDto userDto) {
        return new UserDto(false);
    }

    @PostMapping(value = "/registration")
    public UserDto registration(@RequestBody UserDto userDto) {
        return new UserDto("pawel", "smith", "jonas", "pas", "mak@wp.pl", false, false, false);

    }

    @PutMapping(value = "/editAccount")
    public UserDto editAccount(@RequestBody UserDto userDto) {
        return new UserDto("pawel", "smith", "jonas", "pas", "mak@wp.pl", false, false, false);
    }

    @DeleteMapping(value = "{userId}")
    public void deleteAccount(@PathVariable Long userId) {
    }
}
