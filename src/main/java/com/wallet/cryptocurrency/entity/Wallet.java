package com.wallet.cryptocurrency.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WALLET")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WALLET_ID")
    private Long walletId;

    @Column(name = "WALLET_NAME")
    private String walletName;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "JOIN_COIN_WALLET",
            joinColumns = {@JoinColumn(name = "WALLET_ID", referencedColumnName = "WALLET_ID")},
            inverseJoinColumns = {@JoinColumn(name = "COIN_ID", referencedColumnName = "COIN_ID")}
    )
    private List<Coin> coinList = new ArrayList<>();

    public Wallet(String walletName, User user) {
        this.walletName = walletName;
        this.user = user;
    }

    public Wallet(Long walletId, String walletName, User user) {
        this.walletId = walletId;
        this.walletName = walletName;
        this.user = user;
    }

    public Wallet(Long walletId, String walletName) {
        this.walletId = walletId;
        this.walletName = walletName;
    }
}
