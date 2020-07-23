package com.chaman.userservice.dto;

import com.chaman.userservice.validator.isValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ResetPasswordDto {

    @NotEmpty
    @Size(min = 1)
    @isValidEmail
    private String email;

    @NotEmpty
    @Size(min = 6)
    private String password;
}
