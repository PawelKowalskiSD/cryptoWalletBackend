package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.domain.User;
import com.wallet.cryptocurrency.exceptions.AccountExistsException;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserAccount(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public User saveUserAccount(User user) {
        return userRepository.save(user);
    }

    public void deleteUserAccountById(Long userId) {
        userRepository.deleteById(userId);
    }

    public Optional<User> findUserAccountByMailAddressee(String user) {
        return userRepository.findByMailAddressee(user);

    }
}
