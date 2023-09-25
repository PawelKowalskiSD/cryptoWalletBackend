package com.wallet.cryptocurrency.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wallet.cryptocurrency.entity.JwtToken;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.repository.JwtTokenRepository;
import com.wallet.cryptocurrency.service.CoinService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenRepository jwtTokenRepository;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try {
           String authorization = request.getHeader("Authorization");
           if (authorization != null && authorization.startsWith("Bearer ")) {
               String token = authorization.substring(7);
               if (isJwtToken(token)) {
                   Long userId = extractUserIdFromToken(token);
                   JwtToken jwtToken = jwtTokenRepository.findByToken(token);

                   if (jwtToken != null && !jwtToken.isExpired() && userId.equals(jwtToken.getUser().getUserId())) {
                       UsernamePasswordAuthenticationToken authenticationToken = getUsernamePasswordAuthenticationToken(token, userId);
                       authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                       SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                   }
               }
           }
       } catch (TokenExpiredException e) {
           logger.info("Access denied");

       }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String token, Long id) {
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