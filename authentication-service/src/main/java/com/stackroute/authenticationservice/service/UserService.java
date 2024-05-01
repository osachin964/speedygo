package com.stackroute.authenticationservice.service;

import java.util.Arrays;
import java.util.List;

import com.stackroute.authenticationservice.dto.UserDto;
import com.stackroute.authenticationservice.repository.UserRepository;
import com.stackroute.authenticationservice.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        List<SimpleGrantedAuthority> roles = null;
        Users user = userRepository.findByUsername(username);
        System.out.println(user);
        if (user != null) {
            roles = Arrays.asList(new SimpleGrantedAuthority(String.valueOf(user.getRole())));
            return new User(user.getUsername(), user.getPassword(), roles);
        }

        throw new UsernameNotFoundException("Invalid emailID: " + username);
    }

    public Users saveUser(UserDto user) {
        Users newUser = new Users();
        newUser.setUsername(user.getEmailId());
        newUser.setPassword(user.getPassword());
        newUser.setRole(user.getRole().toString());
        System.out.println(newUser);
        return userRepository.save(newUser);
    }

    public Users getUser(String username){
        return this.userRepository.findByUsername(username);
    }

}
