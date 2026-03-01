package com.duytoan.imajicoffee.imaji_coffee_be.dto.auth;

/**
 * @author duytoan
 * @since Feb 04 2025
 * Dto request change dto
 */
public record PasswordRequestDto(
        String email,
        String password,
        String newPassword
) {
}
