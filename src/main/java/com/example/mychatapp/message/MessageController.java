package com.example.mychatapp.message;

import com.example.mychatapp.buffermessage.BufferMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

@Controller
public class MessageController {

    MessageService messageService;

    SimpMessagingTemplate simpMessagingTemplate;

    BufferMessagesService bufferMessagesService;

    @Autowired
    public MessageController(MessageService messageService, SimpMessagingTemplate simpMessagingTemplate,BufferMessagesService bufferMessagesService) {
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.bufferMessagesService = bufferMessagesService;
    }

    @MessageMapping("/stomp/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel message) {
        if (messageService.checkUserOnline(to)) {
            System.out.println("handling send message: " + message + " to: " + to);
            messageService.saveMessage(message.getMessage(), message.getFromLogin(), to);
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        }else {
            bufferMessagesService.saveBufferMessages(message.getMessage(), message.getFromLogin(), to);
        }
    }
}
