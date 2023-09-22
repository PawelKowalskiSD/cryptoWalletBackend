package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.domain.AuthRequest;
import com.wallet.cryptocurrency.dto.UserDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.mapper.UserMapper;
import com.wallet.cryptocurrency.service.JwtService;
import com.wallet.cryptocurrency.service.UserService;
import com.wallet.cryptocurrency.service.VerifyTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserService userService;
    private final VerifyTokenService verifyTokenService;

    @PostMapping(value = "/log-in")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok().body(jwtService.createToken(authRequest));
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        userService.createUser(user);
        verifyTokenService.createAndSaveToken(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/verify")
    public ResponseEntity<Void> verifyAccount(@RequestParam String verifyToken, @RequestHeader HttpHeaders headers) {
        verifyTokenService.verifyToken(verifyToken);
        headers.add("Location", "/auth/log-in");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
