package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.dto.UserDto;
import com.wallet.cryptocurrency.dto.DashboardDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.exceptions.UserNotFoundException;
import com.wallet.cryptocurrency.facade.DashboardFacade;
import com.wallet.cryptocurrency.mapper.UserMapper;
import com.wallet.cryptocurrency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final DashboardFacade dashboardFacade;

    @GetMapping
    public ResponseEntity<List<DashboardDto>> getUserDashboard() {
        return ResponseEntity.ok().body(dashboardFacade.fetchDashboard());
    }

    @PatchMapping(value = "/edit-account/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> editAccount(@RequestBody UserDto userDto, @PathVariable Long userId) throws UserNotFoundException {
        User user = userService.findUserAccountById(userId);
        userService.editUserAccount(user, userDto);
        return ResponseEntity.ok().body(userMapper.mapToUserDto(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUserAccountById(userId);
        return ResponseEntity.ok().build();
    }
}

