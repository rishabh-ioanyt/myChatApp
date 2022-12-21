package com.example.mychatapp.auth;

import com.example.mychatapp.message.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRegistrationService implements UserDetailsService {

    UserRegistrationRepository userRegistrationRepository;

    DaoAuthenticationProvider daoAuthenticationProvider;

    AuthenticationManager authenticationManager;

    @Autowired
    public UserRegistrationService(@Lazy UserRegistrationRepository userRegistrationRepository,
                                   @Lazy AuthenticationManager authenticationManager,
                                   @Lazy DaoAuthenticationProvider daoAuthenticationProvider) {
        this.userRegistrationRepository = userRegistrationRepository;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRegistrationRepository.findByUsername(username);
    }

    public List<String> getAllUsers() {
        return userRegistrationRepository.findAll().stream().map(UserRegistration::getUsername).collect(Collectors.toList());
    }

    public UserRegistration addUser(UserDto userDto) throws Exception {
        UserRegistration userRegistration = new UserRegistration();
        if (!userRegistrationRepository.existsByUsername(userDto.getUsername())) {
            userRegistration.setUsername(userDto.getUsername());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            userRegistration.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            userRegistrationRepository.save(userRegistration);
        } else {
            throw new Exception("Email already exists!!");
        }
        return userRegistration;
    }

    public String loginUser(UserDto userDto) throws Exception {
        if (userDto.getUsername() != null && userDto.getPassword() != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (userRegistrationRepository.existsByUsername(userDto.getUsername())) {
                UserRegistration registration = userRegistrationRepository.findByUsername(userDto.getUsername());
                if (bCryptPasswordEncoder.matches(userDto.getPassword(), registration.getPassword())) {
                    Authentication authentication = daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return "Logged Successfully";
                } else {
                    return "Password not correct";
                }
            }else {
                return "User Not Found";
            }
        }else {
            return  "Enter Valid Credentials";
        }
    }
}

