package com.wallet.cryptocurrency.repository;

import com.wallet.cryptocurrency.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Override
    Optional<Role> findById(Long id);

    @Override
    Role save(Role role);

    Role findByName(String name);
}
