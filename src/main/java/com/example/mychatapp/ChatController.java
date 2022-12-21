package com.example.mychatapp;

import com.example.mychatapp.auth.UserRegistration;
import com.example.mychatapp.message.MessageModel;
import com.example.mychatapp.message.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class ChatController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    ChatService chatService;



    @GetMapping("/")
    public String indexPage(Model model){
        model.addAttribute("userDto", new UserDto());
        return "index";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute UserDto userRegistration, Model model){
        model.addAttribute("registration", userRegistration);
        model.addAttribute("registrations", chatService.getRegistrationList());
        return "userlist";
    }

    @GetMapping("/chatWith/{username}/{currentUser}")
    public String chatWith(Model model, @PathVariable String username, @PathVariable String currentUser){
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("username", username);
        return "chat";
    }

    @MessageMapping("/stomp/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel message) {
        System.out.println("handling send message: " + message + " to: " + to);
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);

    }

}
