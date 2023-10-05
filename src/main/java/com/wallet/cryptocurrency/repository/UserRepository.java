package com.wallet.cryptocurrency.repository;

import com.wallet.cryptocurrency.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findUsersByMailAddressee(String mail);
}
