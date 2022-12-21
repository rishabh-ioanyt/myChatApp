package com.example.mychatapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChatController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    ChatService chatService;



    @GetMapping("/")
    public String indexPage(Model model){
        model.addAttribute("registration",new Registration());
        return "index";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute Registration registration, Model model){
        chatService.registrationList.add(registration);
        model.addAttribute("registrations", chatService.getRegistrationList(registration));
        return "userlist";
    }

    @GetMapping("/chatWith/{username}")
    public String chatWith(Model model, @PathVariable String username){
        model.addAttribute("username", username);
        return "chat";
    }

    @MessageMapping("/stomp/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel message) {
        System.out.println("handling send message: " + message + " to: " + to);
            simpMessagingTemplate.convertAndSend("/topic/" + to, message);

    }

}
