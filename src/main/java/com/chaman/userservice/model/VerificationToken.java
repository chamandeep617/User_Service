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
public class VerificationToken {

    private static final int VALIDITY_TIME = 4 * 68; // in minutes


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class)
    private User user;

    private Date expiryTime;


    public VerificationToken(){
        super();
    }


    public VerificationToken(User user){
        String token = generateRandomUniqueToken();

        this.token = token;
        this.user = user;
        this.expiryTime = calculateExpiryTime();

    }


    public void updateToken(){

        this.token = generateRandomUniqueToken();
        this.expiryTime = calculateExpiryTime();
    }


    private String generateRandomUniqueToken(){
        return UUID.randomUUID().toString();
    }


    private Date calculateExpiryTime() {

        Calendar calendar = Calendar.getInstance();

        Date currentTimeAndDate = new Date();

        calendar.setTimeInMillis(currentTimeAndDate.getTime());
        calendar.add(Calendar.MINUTE, VALIDITY_TIME);

        return calendar.getTime();
    }
//     universally unique id

}

//scaler.com
// create account
//you get email clicl link
//scaler.com/verify/{}
//click in the link -> which user had this email verification token
// token, userid, expiryTime