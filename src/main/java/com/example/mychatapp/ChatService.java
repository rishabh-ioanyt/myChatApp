package com.example.mychatapp;

import com.example.mychatapp.auth.UserRegistration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    List<UserRegistration> userRegistrationList = new ArrayList<>();

    public List<String> getRegistrationList(){
        return userRegistrationList.stream().map(UserRegistration::getUsername).collect(Collectors.toList());
    }
}
