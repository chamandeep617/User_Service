package com.chaman.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class  SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/register*").permitAll()
                .antMatchers("/").anonymous()
                .antMatchers("/mentee-dashboard*").hasAnyRole("MENTEE","ADMIN")
                .antMatchers("mentor-dashboard*").hasAnyRole("MENTOR","ADMIN")
                .antMatchers("/admin-dashboaord").hasRole("ADMIN")
                .antMatchers("/blah*").hasAuthority("READ_EMAIL_ADDRESS")
                .and().formLogin();
    }
}
