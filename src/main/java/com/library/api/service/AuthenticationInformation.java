package com.library.api.service;

import com.library.api.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
class AuthenticationInformation {
    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !(authentication.isAuthenticated())){
            throw new RuntimeException();
        }

        return (User) authentication.getPrincipal();
    }
}
