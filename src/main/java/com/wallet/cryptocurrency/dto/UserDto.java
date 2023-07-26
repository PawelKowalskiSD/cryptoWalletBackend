package com.wallet.cryptocurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private Long userId;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String mailAddressee;
    private String roleName;

    public UserDto(Long userId, String firstname, String lastname, String username, String password, String mailAddressee) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.mailAddressee = mailAddressee;
    }
}
