package com.wallet.cryptocurrency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VerifyToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VERIFY_TOKEN_ID")
    private Long verifyTokenId;

    @Column(name = "VALUE")
    private String value;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public VerifyToken(String value, User user) {
        this.value = value;
        this.user = user;
    }
}
