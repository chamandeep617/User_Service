package com.chaman.userservice.controller;

import com.chaman.userservice.dto.ResetPasswordDto;
import com.chaman.userservice.dto.ResponseDto;
import com.chaman.userservice.dto.UserResponseDto;
import com.chaman.userservice.model.User;
import com.chaman.userservice.service.UserServiceimpl;
import com.chaman.userservice.validator.isValidEmail;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PasswordResetController {

    @Autowired
    UserServiceimpl userServiceimpl;

    @isValidEmail
    private String email;

    @GetMapping("/user/fetchToken")
    public ResponseDto<UserResponseDto> fetchToken(@RequestParam  String Email){

        System.out.println(Email);
        email = Email;
        User user = userServiceimpl.fetchToken( email);
        return new ResponseDto<>(
                HttpStatus.OK,
                new UserResponseDto(user.getId(), user.getFullname(), user.getEmail(), user.isActive())
        );
    }

    @GetMapping("/user/validate")
    public ResponseDto<UserResponseDto> validateUser(@RequestParam String token){
        User user = userServiceimpl.validateUserToResetPassword(token);
        return new ResponseDto<>(
                HttpStatus.OK,
                new UserResponseDto(user.getId(), user.getFullname(), user.getEmail(), user.isActive())
        );
    }

    @PostMapping("/user/resetPassword")
    public ResponseDto<UserResponseDto> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto){
        User user = userServiceimpl.resetPassword(resetPasswordDto);
        return new ResponseDto<>(
                HttpStatus.OK,
                new UserResponseDto(user.getId(), user.getFullname(), user.getEmail(), user.isActive())
        );

    }
}
