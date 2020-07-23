package com.chaman.userservice.service;

import com.chaman.userservice.dto.ResetPasswordDto;
import com.chaman.userservice.dto.UserDto;
import com.chaman.userservice.event.ResetPasswordEvent;
import com.chaman.userservice.event.SuccessfulRegistrationEvent;
import com.chaman.userservice.model.ResetPasswordToken;
import com.chaman.userservice.model.User;
import com.chaman.userservice.model.VerificationToken;
import com.chaman.userservice.repository.ResetPasswordRepository;
import com.chaman.userservice.repository.UserRepository;
import com.chaman.userservice.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserServiceimpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    ResetPasswordRepository resetPasswordRepository;


    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public User registerUser(UserDto userDto) {

        if(userRepository.findByEmail(userDto.getEmail()) != null){
//        TODO : throw Exception
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFullname(userDto.getFullName());
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // TODO: Encrypt password


        User savedUser = userRepository.save(user);

        applicationEventPublisher.publishEvent(
                new SuccessfulRegistrationEvent(savedUser)
        );

        return savedUser;
    }

    @Override
    public User validateUser(String token) {

//        TODO: check token repo if there is that token
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if(verificationToken == null){
            return null;
        }

        if(verificationToken.getExpiryTime().getTime() - new Date().getTime() > 0){
//            the token is not yet expired
            User verifiedUser = verificationToken.getUser();
            verifiedUser.setActive(true);
            userRepository.save(verifiedUser);
            verificationTokenRepository.delete(verificationToken);
            return verifiedUser;
        }
        User tokenUser = verificationToken.getUser();
//        TODO: if it exists -> check if it is for the same person
//        TODO: check if it is not expired yet

//        if not expired => return verified user
//        else: return null;
        return null;
    }

    @Override
    public User fetchToken(String email) {

        User user = userRepository.findByEmail(email);

        if(user == null){
//            TODO: throw Excpetion for first registering on pllatform
            return null;
        }

        applicationEventPublisher.publishEvent(
                new ResetPasswordEvent(user)
        );
        return user;
    }

    @Override
    public User resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = userRepository.findByEmail(resetPasswordDto.getEmail());

        user.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User validateUserToResetPassword(String token) {
        ResetPasswordToken resetPasswordToken = resetPasswordRepository.findByToken(token);

        if(resetPasswordToken == null){
            return null;
        }
        User verifiedUser = resetPasswordToken.getUser();
        resetPasswordRepository.delete(resetPasswordToken);
        return verifiedUser;
    }
}
