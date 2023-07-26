package com.wallet.cryptocurrency.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "MAIL_ADDRESSEE", unique = true)
    private String mailAddressee;

    @Column(name = "IS_REGISTRATION")
    private boolean isRegistration;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;


    @OneToMany(
            targetEntity = WishList.class,
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<WishList> wishLists = new ArrayList<>();

    @OneToMany(
            targetEntity = Wallet.class,
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<Wallet> walletList = new ArrayList<>();

    public User(Long userId, String firstname, String lastname, String username, String password, String mailAddressee, Role role) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.mailAddressee = mailAddressee;
        this.role = role;
    }
}
