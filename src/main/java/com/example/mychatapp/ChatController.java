package com.example.mychatapp;

import com.example.mychatapp.message.MessageModel;
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



    @GetMapping("/chatWith/{username}/{currentUser}")
    public void chatWith(Model model, @PathVariable String username, @PathVariable String currentUser){
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("username", username);
    }

    @MessageMapping("/stomp/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel message) {
        System.out.println("handling send message: " + message + " to: " + to);
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);

    }

}
