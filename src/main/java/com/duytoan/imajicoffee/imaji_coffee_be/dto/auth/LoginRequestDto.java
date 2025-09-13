package com.duytoan.imajicoffee.imaji_coffee_be.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Invalid email address")
    private String loginInput;

    @Size(min = 6,max = 15, message = "Password is between 6 and 15 characters")
    private String password;
}
