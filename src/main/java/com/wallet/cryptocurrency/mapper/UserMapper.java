package com.wallet.cryptocurrency.mapper;

import com.wallet.cryptocurrency.entity.Role;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.dto.UserDto;
import com.wallet.cryptocurrency.repository.RoleRepository;
import com.wallet.cryptocurrency.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UserMapper {

    private final RoleService roleService;

    public User mapToUser(final UserDto userDto) throws Exception {
        Role role = roleService.findRoleByName(userDto.getRoleName());
        return new User(
                userDto.getUserId(),
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getMailAddressee(),
                role
        );
    }

    public UserDto mapToUserDto(final User user) {

        return new UserDto(
                user.getUserId(),
                user.getFirstname(),
                user.getLastname(),
                user.getUsername(),
                user.getPassword(),
                user.getMailAddressee()
        );
    }
}
