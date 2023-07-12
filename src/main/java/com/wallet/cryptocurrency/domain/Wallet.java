package com.wallet.cryptocurrency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "WALLET")
public class Wallet {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "WALLET_ID")
    private Long walletId;

    @Column(name = "WALLET_NAME")
    private String walletName;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "WALLET_STATS_ID")
    private WalletStats walletStats;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_TOKEN_WALLET",
            joinColumns = {@JoinColumn(name = "WALLET_ID", referencedColumnName = "WALLET_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TOKEN_ID", referencedColumnName = "TOKEN_ID")}
    )
    private List<Token> tokenList = new ArrayList<>();
}
