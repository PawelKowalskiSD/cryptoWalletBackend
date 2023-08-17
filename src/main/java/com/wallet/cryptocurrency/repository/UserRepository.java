package com.wallet.cryptocurrency.repository;

import com.wallet.cryptocurrency.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    Optional<User> findById(Long id);

    @Override
    User save(User user);

    @Override
    void deleteById(Long id);

    void deleteByUsername(String username);

    Optional<User> findByUsername(String username);
}
