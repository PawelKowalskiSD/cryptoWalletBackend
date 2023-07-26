package com.wallet.cryptocurrency.controller;

import com.wallet.cryptocurrency.dto.RoleDto;
import com.wallet.cryptocurrency.entity.Role;
import com.wallet.cryptocurrency.mapper.RoleMapper;
import com.wallet.cryptocurrency.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RequestMapping("/v1/role")
@RestController
public class RoleController {

    private final RoleService roleService;

    private final RoleMapper roleMapper;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        Role role = roleMapper.mapToRole(roleDto);
        roleService.saveRole(role);
        return ResponseEntity.ok().build();
    }
}
