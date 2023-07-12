package com.wallet.cryptocurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String firstname;
    private String lastname;
    private String nickName;
    private String password;
    private String mailAddressee;
    private boolean isRegistration;
    private boolean isLogged;
    private boolean isExtraKey;


    public UserDto(String password, String mailAddressee, boolean isRegistration, boolean isLogged, boolean isExtraKey) {
        this.password = password;
        this.mailAddressee = mailAddressee;
        this.isRegistration = isRegistration;
        this.isLogged = isLogged;
        this.isExtraKey = isExtraKey;
    }

    public UserDto(boolean isLogged) {
        this.isLogged = isLogged;
    }

}
