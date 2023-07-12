package com.wallet.cryptocurrency.mapper;

import com.wallet.cryptocurrency.domain.User;
import com.wallet.cryptocurrency.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getNickName(),
                userDto.getPassword(),
                userDto.getMailAddressee()
        );
    }
}
