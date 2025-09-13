package com.duytoan.imajicoffee.imaji_coffee_be.security;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.User;
import com.duytoan.imajicoffee.imaji_coffee_be.exceptions.ResourceNotFoundException;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String loginInput) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(loginInput)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", loginInput));

        return new CustomUserDetails(user);
    }
}
