package com.wallet.cryptocurrency.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wallet.cryptocurrency.entity.JwtToken;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.repository.JwtTokenRepository;
import com.wallet.cryptocurrency.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenRepository jwtTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
//        String username;
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.substring(7);
//        username = jwtTokenRepository.findJwtTokenByToken(token).map(t -> t.getUser().getUsername()).orElse(null);
        if (/*username != null && */SecurityContextHolder.getContext().getAuthentication() == null && isJwtToken(token)) {
            Long userId = extractUserIdFromToken(token);
            JwtToken test = jwtTokenRepository.findByToken(token);
            if (!test.isExpired() && userId.equals(test.getUser().getUserId())) {
                UsernamePasswordAuthenticationToken authenticationToken = getUsernamePasswordAuthenticationToken(token, userId);

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String token, Long id) {
        DecodedJWT jwt = getDecodedJWT(token);
        String[] roles = jwt.getClaim("roles").asArray(String.class);
        List<SimpleGrantedAuthority> collect = Stream.of(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        User user = new User(id);
        return new UsernamePasswordAuthenticationToken(user, null, collect);
    }

    private Long extractUserIdFromToken(String token) {
        DecodedJWT jwt = getDecodedJWT(token);
        Long userId = jwt.getClaim("id").asLong();
        if (userId == null) {
            throw new RuntimeException("Token does not contain a valid user ID");
        }
        return userId;
    }

    private static DecodedJWT getDecodedJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC512("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

    private boolean isJwtToken(String token) {
        String[] tokenParts = token.split("\\.");
        return tokenParts.length == 3;
    }
}