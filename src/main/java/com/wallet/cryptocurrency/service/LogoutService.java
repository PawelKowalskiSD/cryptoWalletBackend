package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.entity.JwtToken;
import com.wallet.cryptocurrency.repository.JwtTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogoutService implements LogoutHandler {
    private final JwtTokenRepository jwtTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String auth = request.getHeader("Authorization");
        String tokenJwt;
        if (auth == null || !auth.startsWith("Bearer ")) {
            return;
        }
        tokenJwt = auth.substring(7);
        JwtToken soredToken = jwtTokenRepository.findJwtTokenByToken(tokenJwt).orElse(null);
        if (soredToken != null) {
            soredToken.setExpired(true);
            jwtTokenRepository.save(soredToken);
            SecurityContextHolder.clearContext();
        }

    }
}
