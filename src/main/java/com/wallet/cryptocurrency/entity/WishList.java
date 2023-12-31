package com.wallet.cryptocurrency.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WISH_LIST")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WISH_LIST_ID")
    private Long wishListId;

    @Column(name = "WISH_LIST_NAME")
    private String wishListName;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "JOIN_COIN_WISH_LIST",
            joinColumns = {@JoinColumn(name = "WISH_LIST_ID", referencedColumnName = "WISH_LIST_ID")},
            inverseJoinColumns = {@JoinColumn(name = "COIN_ID", referencedColumnName = "COIN_ID")}
    )
    private List<Coin> coinWishList = new ArrayList<>();

    public WishList(Long wishListId, String wishListName) {
        this.wishListId = wishListId;
        this.wishListName = wishListName;
    }

    public WishList(Long wishListId, String wishListName, User user) {
        this.wishListId = wishListId;
        this.wishListName = wishListName;
        this.user = user;
    }
}
