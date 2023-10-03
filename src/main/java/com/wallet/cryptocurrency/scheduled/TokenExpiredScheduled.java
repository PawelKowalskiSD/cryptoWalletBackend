package com.wallet.cryptocurrency.scheduled;

import com.wallet.cryptocurrency.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class TokenExpiredScheduled {
    private final JwtService jwtService;

    @Scheduled(cron = "0 * * * * *")
    public void scheduleTokenExpired() {
        jwtService.findAllExpiredToken();
    }
}
