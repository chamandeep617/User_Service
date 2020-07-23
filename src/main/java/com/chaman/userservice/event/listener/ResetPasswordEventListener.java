package com.chaman.userservice.event.listener;

import com.chaman.userservice.event.ResetPasswordEvent;
import com.chaman.userservice.model.ResetPasswordToken;
import com.chaman.userservice.model.User;
import com.chaman.userservice.model.VerificationToken;
import com.chaman.userservice.repository.ResetPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ResetPasswordEventListener implements ApplicationListener<ResetPasswordEvent> {

    @Autowired
    ResetPasswordRepository resetPasswordRepository;

    @Override
    public void onApplicationEvent(ResetPasswordEvent resetPasswordEvent) {

        User registeredUser = resetPasswordEvent.getRegisteredUser();

        ResetPasswordToken token = new ResetPasswordToken(registeredUser);

        resetPasswordRepository.save(token);

        //        TODO: send email to the user
    }
}
