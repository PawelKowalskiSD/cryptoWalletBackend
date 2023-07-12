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
@Table(name = "WISH_LIST")
public class WishList {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "WISH_LIST_ID")
    private Long wishListId;

    @Column(name = "WISH_LIST_NAME")
    private String wishListName;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @Column(name = "PRICE_TARGET")
    private BigDecimal priceTarget;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_TOKEN_WISH_LIST",
            joinColumns = {@JoinColumn(name = "WISH_LIST_ID", referencedColumnName = "WISH_LIST_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TOKEN_ID", referencedColumnName = "TOKEN_ID")}
    )
    private List<Token> tokenList = new ArrayList<>();
}
