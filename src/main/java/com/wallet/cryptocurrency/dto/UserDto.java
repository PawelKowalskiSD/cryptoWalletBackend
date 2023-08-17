package com.wallet.cryptocurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    private String role;
}
