package com.duytoan.imajicoffee.imaji_coffee_be.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String email;
    private String username;
    private String password;
}
