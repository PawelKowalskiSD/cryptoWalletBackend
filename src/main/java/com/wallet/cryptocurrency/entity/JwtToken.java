package com.wallet.cryptocurrency.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private boolean expired;

    private LocalDate date;
    private Time time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public JwtToken(String token, boolean expired, LocalDate date, Time dateExpiredToken, User user) {
        this.token = token;
        this.expired = expired;
        this.date = date;
        this.time = dateExpiredToken;
        this.user = user;
    }
}
