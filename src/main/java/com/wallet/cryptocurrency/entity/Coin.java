package com.wallet.cryptocurrency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "COIN")
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COIN_ID", unique = true)
    private Long coinId;

    @Column(name = "COIN_NAME")
    private String tokenName;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "QUANTITY", precision = 15, scale = 10)
    private BigDecimal quantity;

    @Column(name = "URL_IMAGES")
    private String urlImages;

    @ManyToMany(mappedBy = "coinList")
    private List<Wallet> walletList = new ArrayList<>();

    @ManyToMany(mappedBy = "coinList")
    private List<WishList> wishLists = new ArrayList<>();

}
