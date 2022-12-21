package com.example.mychatapp;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    List<Registration> registrationList = new ArrayList<>();

    public List<String> getRegistrationList(Registration registration){
        List<String> active = new ArrayList<>();
        registrationList.forEach(registration1 -> {
            if (registration.getUsername() != registration1.getUsername()){
                active.add(registration1.getUsername());
            }
        });
        return active;
    }
}
