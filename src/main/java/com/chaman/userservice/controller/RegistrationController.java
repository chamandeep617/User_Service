package com.chaman.userservice.controller;

import com.chaman.userservice.dto.ResponseDto;
import com.chaman.userservice.dto.UserDto;
import com.chaman.userservice.dto.UserResponseDto;
import com.chaman.userservice.model.User;
import com.chaman.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public ResponseDto<UserResponseDto> registerUser(@Valid @RequestBody UserDto userDto) {

        log.info("Recieved a registration request",userDto.getEmail());
        User user = userService.registerUser(userDto);
        return new ResponseDto<>(
                HttpStatus.OK,
                new UserResponseDto(user.getId(), user.getFullname(), user.getEmail(), user.isActive())
        );

    }

    @GetMapping("/user/confirm")
    public ResponseDto<UserResponseDto> validateUser(@RequestParam String token){
        User user = userService.validateUser(token);
        return new ResponseDto<>(
                HttpStatus.OK,
                new UserResponseDto(user.getId(), user.getFullname(), user.getEmail(), user.isActive())
        );
    }


}
