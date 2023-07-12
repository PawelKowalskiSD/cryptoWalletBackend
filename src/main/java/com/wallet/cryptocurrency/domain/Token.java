package com.wallet.cryptocurrency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "TOKEN")
public class Token {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "TOKEN_ID")
    private Long tokenId;

    @Column(name = "TOKEN_NAME")
    private String tokenName;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @Column(name = "AVERAGE_PURCHASE_PRICE")
    private BigDecimal averagePurchasePrice;

    @Column(name = "AVERAGE_SALE_PRICE")
    private BigDecimal averageSalePrice;

    @Column(name = "CURRENT_PRICE")
    private BigDecimal currentPrice;

    @Column(name = "MARKET_CAP")
    private BigDecimal marketCap;

    @ManyToMany( mappedBy = "tokenList")
    private List<Wallet> walletList = new ArrayList<>();

    @ManyToMany( mappedBy = "tokenList")
    private List<WishList> wishLists = new ArrayList<>();
}
