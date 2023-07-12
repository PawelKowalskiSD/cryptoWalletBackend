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
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "USER_ID")
    private Long userId;

    @NotNull
    @Column(name = "FIRSTNAME")
    private String firstname;

    @NotNull
    @Column(name = "LASTNAME")
    private String lastname;

    @NotNull
    @Column(name = "NICK_NAME")
    private String nickName;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Column(name = "MAIL_ADDRESSEE")
    private String mailAddressee;

    @NotNull
    @Column(name = "IS_REGISTRATION")
    private boolean isRegistration;

    @NotNull
    @Column(name = "IS_LOGGED")
    private boolean isLogged;

    @NotNull
    @Column(name = "IS_EXTRA_KEY")
    private boolean isExtraKey;

    @OneToMany(
            targetEntity = WishList.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<WishList> wishLists = new ArrayList<>();

    @OneToMany(
            targetEntity = Wallet.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Wallet> walletList = new ArrayList<>();

}
