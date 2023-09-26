package com.wallet.cryptocurrency.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USER")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "MAIL_ADDRESSEE", unique = true)
    private String mailAddressee;

    @Column(name = "IS_ENABLED")
    private boolean isEnabled;

    @Column(name = "ROLE")
    private String role;

    @OneToMany(
            targetEntity = WishList.class,
            mappedBy = "user",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<WishList> wishLists = new ArrayList<>();

    @OneToMany(
            targetEntity = Wallet.class,
            mappedBy = "user",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<Wallet> walletList = new ArrayList<>();
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private VerifyToken verifyToken;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<JwtToken> jwtTokens = new ArrayList<>();

    public User(Long userId, String firstname, String lastname, String username, String password, String mailAddressee, String role) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.mailAddressee = mailAddressee;
        this.role = role;
    }

    public User(Long userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(Long userId) {
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
