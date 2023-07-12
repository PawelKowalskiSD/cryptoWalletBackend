package com.wallet.cryptocurrency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Column(name = "MAIL_ADDRESSEE", unique = true)
    private String mailAddressee;


    @Column(name = "IS_REGISTRATION")
    private boolean isRegistration;


    @Column(name = "IS_LOGGED")
    private boolean isLogged;


    @Column(name = "IS_EXTRA_KEY")
    private boolean isExtraKey;

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

    public User(String firstname, String lastname, String nickName, String password, String mailAddressee, boolean isRegistration, boolean isLogged, boolean isExtraKey) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickName = nickName;
        this.password = password;
        this.mailAddressee = mailAddressee;
        this.isRegistration = isRegistration;
        this.isLogged = isLogged;
        this.isExtraKey = isExtraKey;
    }

    public User(String firstname, String lastname, String nickName, String password, String mailAddressee) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickName = nickName;
        this.password = password;
        this.mailAddressee = mailAddressee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (isRegistration != user.isRegistration) return false;
        if (isLogged != user.isLogged) return false;
        if (isExtraKey != user.isExtraKey) return false;
        if (!Objects.equals(userId, user.userId)) return false;
        if (!Objects.equals(firstname, user.firstname)) return false;
        if (!Objects.equals(lastname, user.lastname)) return false;
        if (!Objects.equals(nickName, user.nickName)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(mailAddressee, user.mailAddressee))
            return false;
        if (!Objects.equals(wishLists, user.wishLists)) return false;
        return Objects.equals(walletList, user.walletList);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (mailAddressee != null ? mailAddressee.hashCode() : 0);
        result = 31 * result + (isRegistration ? 1 : 0);
        result = 31 * result + (isLogged ? 1 : 0);
        result = 31 * result + (isExtraKey ? 1 : 0);
        result = 31 * result + (wishLists != null ? wishLists.hashCode() : 0);
        result = 31 * result + (walletList != null ? walletList.hashCode() : 0);
        return result;
    }
}
