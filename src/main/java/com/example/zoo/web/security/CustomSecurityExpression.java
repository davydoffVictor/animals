package com.example.zoo.web.security;

import com.example.zoo.domain.user.Role;
import com.example.zoo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("cse")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;

    public boolean canAccessUser(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userId.equals(id) || hasRole(authentication, Role.ROLE_ADMIN);
    }

    public boolean canAccessAnimal(Long animalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userService.isAnimalOwner(userId, animalId) || hasRole(authentication, Role.ROLE_ADMIN);
    }

    private boolean hasRole(Authentication authentication, Role role) {
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority(role.name()));
    }
}
