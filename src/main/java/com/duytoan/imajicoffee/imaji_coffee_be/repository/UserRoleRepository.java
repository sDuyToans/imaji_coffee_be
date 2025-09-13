package com.duytoan.imajicoffee.imaji_coffee_be.repository;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    boolean existsByUser_UserId(Long userId);
}
