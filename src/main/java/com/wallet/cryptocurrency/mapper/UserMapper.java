package com.wallet.cryptocurrency.mapper;

import com.wallet.cryptocurrency.domain.Role;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UserMapper {


    public User mapToUser(final UserDto userDto) throws Exception {

        return new User(
                userDto.getUserId(),
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getMailAddressee(),
                userDto.getRoleName()
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
