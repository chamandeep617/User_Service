package com.chaman.userservice.service;

import com.chaman.userservice.dto.ResetPasswordDto;
import com.chaman.userservice.dto.UserDto;
import com.chaman.userservice.dto.UserResponseDto;
import com.chaman.userservice.model.User;

public interface UserService {
    public User registerUser(UserDto userDto);

    public User validateUser(String token);

    public User fetchToken(String email);

    User resetPassword(ResetPasswordDto resetPasswordDto);

    User validateUserToResetPassword(String token);
}
