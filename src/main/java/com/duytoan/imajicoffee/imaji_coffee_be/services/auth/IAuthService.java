package com.duytoan.imajicoffee.imaji_coffee_be.services.auth;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.auth.LoginRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.auth.SignUpRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.SignupStatus;

public interface IAuthService {
    public SignupStatus singup(SignUpRequestDto signUpRequestDto);
    public String login(LoginRequestDto loginRequestDto);
}
