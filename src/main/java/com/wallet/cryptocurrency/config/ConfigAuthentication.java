package com.wallet.cryptocurrency.config;

import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.exceptions.UserPermissionsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ConfigAuthentication {

    public Long getUserIdFromAuthentication(Authentication authentication) throws UserPermissionsException {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserPermissionsException();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            return ((User) principal).getUserId();
        } else {
            throw new UserPermissionsException();
        }
    }
}
