package com.wallet.cryptocurrency.mapper;

import com.wallet.cryptocurrency.dto.RoleDto;
import com.wallet.cryptocurrency.entity.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper {

    public Role mapToRole(final RoleDto roleDto) {
        return new Role(
                roleDto.getName()
        );
    }
}
