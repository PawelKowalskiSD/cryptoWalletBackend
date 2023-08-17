package com.wallet.cryptocurrency.mapper;

import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserMapper {

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getUserId(),
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getMailAddressee(),
                userDto.getRole()
        );
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto(
                user.getUserId(),
                user.getFirstname(),
                user.getLastname(),
                user.getUsername(),
                user.getPassword(),
                user.getMailAddressee(),
                user.getRole()
        );
    }
}
