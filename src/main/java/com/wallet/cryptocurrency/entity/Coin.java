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
    private String coinName;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "QUANTITY", precision = 15, scale = 7)
    private BigDecimal quantity;

    @Column(name = "AVERAGE_PURCHASE_PRICE", precision = 15, scale = 10)
    private BigDecimal averagePurchasePrice;

    @Column(name = "AVERAGE_SALE_PRICE", precision = 15, scale = 10)
    private BigDecimal averageSalePrice;

    @Column(name = "CURRENT_PRICE", precision = 15, scale = 10)
    private BigDecimal currentPrice;

    @Column(name = "PURCHASE_WISH_PRICE", precision = 15, scale = 10)
    private BigDecimal purchaseWishPrice;

    @Column(name = "MARKET_CAP")
    private BigDecimal marketCap;

    @Column(name = "MARKET_CAP_RANK")
    private Long marketCapRank;

    @Column(name = "HIGH_24h", precision = 15, scale = 10)
    private BigDecimal high24h;

    @Column(name = "LOW_24h", precision = 15, scale = 10)
    private BigDecimal low24h;

    @Column(name = "CIRCULATING_SUPPLY")
    private BigDecimal circulatingSupply;

    @Column(name = "TOTAL_SUPPLY")
    private BigDecimal totalSupply;

    @Column(name = "INVESTED_FUNDS")
    private BigDecimal investedFunds;

    @ManyToMany
    private List<Wallet> walletList = new ArrayList<>();

    @ManyToMany(mappedBy = "coinWishList")
    private List<WishList> wishLists = new ArrayList<>();
}
