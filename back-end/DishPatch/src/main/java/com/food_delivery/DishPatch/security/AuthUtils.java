package com.food_delivery.DishPatch.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * - The authorities are plain strings like "SUPERADMIN".
 * - JwtAuthenticationFilter sets SecurityContext with new JwtAuthentication(userId, role).
 */
@Component
public class AuthUtils {

    public Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isAuthenticated() {
        Authentication a = authentication();
        return a != null && a.isAuthenticated();
    }

    public Long currentUserId() {
        Authentication a = authentication();
        if (a == null) return null;

        if (a instanceof JwtAuthentication jwt) {
            return jwt.userId();
        }

        Object principal = a.getPrincipal();
        if (principal instanceof JwtAuthentication jwt) {
            return jwt.userId();
        }

        return null;
    }

    public String currentRole() {
        Authentication a = authentication();
        if (a == null) return null;

        if (a instanceof JwtAuthentication jwt) {
            return jwt.role();
        }

        Object principal = a.getPrincipal();
        if (principal instanceof JwtAuthentication jwt) {
            return jwt.role();
        }

        return null;
    }

    public Set<String> authorities() {
        Authentication a = authentication();
        if (a == null) return Set.of();

        Collection<? extends GrantedAuthority> auths = a.getAuthorities();
        if (auths == null) return Set.of();

        return auths.stream()
                .filter(Objects::nonNull)
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toUnmodifiableSet());
    }

    public boolean hasAuthority(String authority) {
        if (authority == null) return false;
        return authorities().contains(authority);
    }

    public boolean hasAnyAuthority(String... authorities) {
        if (authorities == null) return false;
        Set<String> current = authorities();
        for (String a : authorities) {
            if (a != null && current.contains(a)) return true;
        }
        return false;
    }

    public boolean hasRole(String role) {
        return hasAuthority(role);
    }

    public boolean hasAnyRole(String... roles) {
        return hasAnyAuthority(roles);
    }
}
