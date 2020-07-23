package com.chaman.userservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ResetPasswordToken {
    private static final int VALIDITY_TIME = 4 * 68; // in minutes


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class)
    private User user;


    public ResetPasswordToken(){
        super();
    }


    public ResetPasswordToken(User user){
        String token = generateRandomUniqueToken();

        this.token = token;
        this.user = user;

    }

    private String generateRandomUniqueToken(){
        return UUID.randomUUID().toString();
    }
}
