package com.chaman.userservice.event.listener;

import com.chaman.userservice.event.SuccessfulRegistrationEvent;
import com.chaman.userservice.model.User;
import com.chaman.userservice.model.VerificationToken;
import com.chaman.userservice.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SuccessfulRegistrationEventListener implements ApplicationListener<SuccessfulRegistrationEvent> {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Override
    public void onApplicationEvent(SuccessfulRegistrationEvent successfulRegistrationEvent) {

        User registeredUser = successfulRegistrationEvent.getRegisteredUser();

        VerificationToken token = new VerificationToken(registeredUser);

        verificationTokenRepository.save(token);

        //        TODO: send email to the user

    }
}
