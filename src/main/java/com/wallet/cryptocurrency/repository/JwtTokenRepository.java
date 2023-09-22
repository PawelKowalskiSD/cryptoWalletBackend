package com.wallet.cryptocurrency.repository;

import com.wallet.cryptocurrency.entity.JwtToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JwtTokenRepository extends CrudRepository<JwtToken, Long> {
    Optional<JwtToken> findJwtTokenByToken(String token);

    @Query(value = """
            select token from JwtToken token inner join User user\s
            on token.user.userId = user.userId\s
            where user.userId = :userId and (token.expired = false)\s
            """)
    List<JwtToken> findAllJwtTokenByUser(Long userId);

    JwtToken findByToken(String token);
}
