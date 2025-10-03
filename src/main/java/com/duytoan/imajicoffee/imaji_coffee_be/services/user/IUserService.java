package com.duytoan.imajicoffee.imaji_coffee_be.services.user;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.user.UserDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.User;

public interface IUserService {
    public UserDto getUser(Long userId);
    public User createUserOAuth2(String email, String username);
}
