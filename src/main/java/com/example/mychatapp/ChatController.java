package com.example.mychatapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    List<Registration> registrations = new ArrayList<>();

    @GetMapping("/")
    public String indexPage(Model model){
        model.addAttribute("registration",new Registration());
        return "index";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute Registration registration, Model model){
        registrations.add(registration);
        model.addAttribute("registrations", registrations);
        return "userlist";
    }

    @GetMapping("/disconnect")
    public String disconnect(Model model){
        model.addAttribute("registration",new Registration());
        return "index";
    }

    @GetMapping("/chatWith/{username}")
    public String chatWith(Model model, @PathVariable String username){
        model.addAttribute("username", username);
        return "chat";
    }
}
