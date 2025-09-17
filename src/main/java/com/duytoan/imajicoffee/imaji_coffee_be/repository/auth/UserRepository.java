package com.duytoan.imajicoffee.imaji_coffee_be.repository.auth;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);
}
