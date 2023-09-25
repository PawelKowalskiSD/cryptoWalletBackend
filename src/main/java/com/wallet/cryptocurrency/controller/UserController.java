package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.config.ConfigAuthentication;
import com.wallet.cryptocurrency.dto.DashboardDto;
import com.wallet.cryptocurrency.dto.UserDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.exceptions.UserPermissionsException;
import com.wallet.cryptocurrency.facade.DashboardFacade;
import com.wallet.cryptocurrency.mapper.UserMapper;
import com.wallet.cryptocurrency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final DashboardFacade dashboardFacade;
    private final ConfigAuthentication configAuthentication;

    @GetMapping
    public ResponseEntity<List<DashboardDto>> getUserDashboard(Authentication authentication) throws UserPermissionsException {
        return ResponseEntity.ok().body(dashboardFacade.fetchDashboard(authentication));
    }

    @PatchMapping(value = "/edit-account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> editAccount(@RequestBody UserDto userDto, Authentication authentication) throws UserNotFoundException, UserPermissionsException {
        Long userId = configAuthentication.getUserIdFromAuthentication(authentication);
        User user = userService.findUserAccountById(userId);
        userService.editUserAccount(user, userDto);
        return ResponseEntity.ok().body(userMapper.mapToUserDto(user));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> deleteUser(Authentication authentication) throws UserPermissionsException {
        Long userId = configAuthentication.getUserIdFromAuthentication(authentication);
        userService.deleteUserAccountById(userId);
        return ResponseEntity.ok().build();
    }
}

