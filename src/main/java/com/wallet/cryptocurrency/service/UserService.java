package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.domain.Role;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserAccount(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void createUser(User user) throws MessagingException {
        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setMailAddressee(user.getMailAddressee());
        user.setRole(Role.USER.toString());

        userRepository.save(user);
    }

    public User saveUserAccount(User user) {
        return userRepository.save(user);
    }

    public void deleteUserAccountById(Long userId) {
        userRepository.deleteById(userId);
    }

    public User findUserAccountByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }
}
