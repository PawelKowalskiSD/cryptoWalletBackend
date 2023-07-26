package com.wallet.cryptocurrency.validator;

import com.wallet.cryptocurrency.dto.UserDto;
import com.wallet.cryptocurrency.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validateUseraddExtraKey(final UserDto userDto, User user) {
        if (userDto.getFirstname() != null) {
            user.setFirstname(userDto.getFirstname());
        }
        if (userDto.getLastname() != null) {
            user.setLastname(userDto.getLastname());
        }
        if (userDto.getUsername() !=null) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getPassword() !=null) {
            user.setPassword(userDto.getPassword());
        }
        if (userDto.getMailAddressee() != null) {
            user.setMailAddressee(userDto.getMailAddressee());
        }
    }
}
