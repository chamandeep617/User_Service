package com.chaman.userservice.service.security;

import com.chaman.userservice.model.Permission;
import com.chaman.userservice.model.Role;
import com.chaman.userservice.model.User;
import com.chaman.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);

        if(user == null){
            throw new UsernameNotFoundException("No user found with that name");
        }

//        fetch role for that Particular user ->list[String ]
//        fetch permissions for that particular role - > list[String]
//        map the role and privlidges to spring
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            user.isActive(),
            true,
            true,
            true,
            getAuthorities(user.getRoles())
        );

//        roles -> ["ADMIN","Career_Coach","Mentor"]
    }

    private List<GrantedAuthority> getAuthorities(List<Role> userRoles){

        List<GrantedAuthority> authorities = new ArrayList<>();

        for(Role role: userRoles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            for(Permission permission : role.getPermissions() ){
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }

        return authorities;
    }
}
