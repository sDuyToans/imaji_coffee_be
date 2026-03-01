package com.duytoan.imajicoffee.imaji_coffee_be.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class GetAuthenticationInfo {

    @Bean
    public Long getUserId(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }
}
