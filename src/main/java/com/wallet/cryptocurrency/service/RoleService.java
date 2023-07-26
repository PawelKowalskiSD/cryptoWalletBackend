package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.entity.Role;
import com.wallet.cryptocurrency.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findRoleById(Long id) throws Exception {
        return roleRepository.findById(id).orElseThrow(Exception::new);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
