package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.dto.UserDto;
import com.wallet.cryptocurrency.entity.Role;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public User getUserAccount(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void createUser(User user) throws Exception {
        Role role = roleService.findRoleByName("user");

        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setMailAddressee(user.getMailAddressee());
        user.setRole(role);
    }

    public void logInToAccount(User user) {
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
