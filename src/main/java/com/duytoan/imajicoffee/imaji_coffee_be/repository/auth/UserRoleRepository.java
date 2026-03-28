package com.duytoan.imajicoffee.imaji_coffee_be.repository.auth;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User role repository
 * @author duytoan
 * @since 10/2025
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    /**
     * Check if user's role exist
     * @param userId
     * @return exists or not
     */
    boolean existsByUser_UserId(Long userId);
}
