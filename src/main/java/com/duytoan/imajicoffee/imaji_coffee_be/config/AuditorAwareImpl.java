package com.duytoan.imajicoffee.imaji_coffee_be.config;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.User;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Auditor aware implementation, "who did what"
 * @author duytoan
 * @since 10/2025
 */
@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<User> {
    @Override
    @NonNull
    public Optional<User> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(User.class::cast);
    }
}
