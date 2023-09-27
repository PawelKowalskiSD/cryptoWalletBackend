package com.wallet.cryptocurrency.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wallet.cryptocurrency.domain.AuthRequest;
import com.wallet.cryptocurrency.domain.AuthResponse;
import com.wallet.cryptocurrency.entity.JwtToken;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.repository.JwtTokenRepository;
import com.wallet.cryptocurrency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;

    public AuthResponse createToken(AuthRequest authRequest) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        User user = (User) authenticate.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC512("secret");
        String token = JWT.create()
                .withClaim("id", user.getUserId())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        revokeAllUserTokens(user);
        saveToken(token, user);
        userRepository.save(user);
        return new AuthResponse(user.getUsername(), token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserToken = jwtTokenRepository.findAllJwtTokenByUser(user.getUserId());
        if (validUserToken.isEmpty())
            return;
        validUserToken.forEach(token -> {
            token.setExpired(true);
        });
    }


    private JwtToken saveToken(String token, User user) {
        JwtToken jwtToken = new JwtToken(token, false, LocalDate.now(), Time.valueOf(LocalTime.now()), user);
        return jwtTokenRepository.save(jwtToken);
    }


}
