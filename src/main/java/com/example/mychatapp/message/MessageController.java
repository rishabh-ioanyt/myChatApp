package com.example.mychatapp.message;

import com.example.mychatapp.buffermessage.BufferMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Controller
public class MessageController {

    MessageService messageService;

    SimpMessagingTemplate simpMessagingTemplate;

    BufferMessagesService bufferMessagesService;

    @Autowired
    public MessageController(MessageService messageService, SimpMessagingTemplate simpMessagingTemplate, BufferMessagesService bufferMessagesService) {
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
        } else {
            bufferMessagesService.saveBufferMessages(message.getMessage(), message.getFromLogin(), to);
        }
    }

    @RequestMapping("/sse")
    public SseEmitter sse() {
        return messageService.addUser();
    }

    @MessageMapping("/stomp/broadCast")
    public void sendMessage(MessageModel message) {
        simpMessagingTemplate.convertAndSend("/topic/messages", message);
    }

    @MessageMapping("/getMessageBySenderAndReceiver/{sender}/{receiver}")
    public void getMessageBySenderAndReceiver(@DestinationVariable String sender, @DestinationVariable String receiver){
        simpMessagingTemplate.convertAndSend("/topic/getMessages/"+sender, messageService.getMessageBySenderAndReceiver(sender,receiver));
    }
}
