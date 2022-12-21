package com.example.mychatapp;

import com.example.mychatapp.auth.UserRegistration;
import com.example.mychatapp.message.MessageModel;
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
        model.addAttribute("registration",new UserRegistration());
        return "index";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute UserRegistration userRegistration, Model model){
        chatService.userRegistrationList.add(userRegistration);
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
