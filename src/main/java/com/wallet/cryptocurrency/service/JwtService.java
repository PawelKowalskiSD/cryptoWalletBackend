package com.wallet.cryptocurrency.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wallet.cryptocurrency.domain.AuthRequest;
import com.wallet.cryptocurrency.domain.AuthResponse;
import com.wallet.cryptocurrency.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final AuthenticationManager authenticationManager;

    public AuthResponse createToken(AuthRequest authRequest) {

        Authentication authenticate = authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        User user = (User) authenticate.getPrincipal();

        Algorithm algorithm = Algorithm.HMAC512("secret");
        String token = JWT.create()
                .withClaim("id", user.getUserId())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        return new AuthResponse(user.getUsername(), token);
    }

}
