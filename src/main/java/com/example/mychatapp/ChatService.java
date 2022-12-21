package com.example.mychatapp;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    List<Registration> registrationList = new ArrayList<>();

    public List<String> getRegistrationList(){
        return registrationList.stream().map(Registration::getUsername).collect(Collectors.toList());
    }
}
