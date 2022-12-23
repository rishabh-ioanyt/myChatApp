package com.example.mychatapp.events;

import com.example.mychatapp.buffermessage.BufferMessagesRepository;
import com.example.mychatapp.message.MessageModel;
import com.example.mychatapp.message.MessageService;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.session.web.socket.events.SessionConnectEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Timer;
import java.util.TimerTask;

@Component
@Log4j2
@WebListener
public class SessionConnectEventListen implements ApplicationListener<SessionConnectEvent> {

    BufferMessagesRepository bufferMessagesRepository;

    SimpMessagingTemplate simpMessagingTemplate;

    MessageService messageService;

    @Autowired
    public SessionConnectEventListen(BufferMessagesRepository bufferMessagesRepository, SimpMessagingTemplate simpMessagingTemplate, MessageService messageService) {
        this.bufferMessagesRepository = bufferMessagesRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
    }

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        String username = event.getWebSocketSession().getPrincipal().getName();
        log.info("WebSocket Session Created by: "+username+" with Session Id :"+ event.getWebSocketSession().getId());
        if (bufferMessagesRepository.countAllByReceivedByUsername(event.getWebSocketSession().getPrincipal().getName()) > 0) {
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    bufferMessagesRepository.findAllByReceivedByUsername(username).forEach(bufferMessages -> {
                        messageService.saveMessage(bufferMessages.getMessage(), bufferMessages.getSendBy().getUsername(), bufferMessages.getReceivedBy().getUsername());
                        MessageModel message = new MessageModel();
                        message.setMessage(bufferMessages.getMessage());
                        message.setFromLogin(bufferMessages.getSendBy().getUsername());
                        simpMessagingTemplate.convertAndSend("/topic/messages/" + bufferMessages.getReceivedBy().getUsername(),message);
                    });
                    bufferMessagesRepository.deleteAllByReceivedByUsername(username);
                }
            };
            timer.schedule(timerTask, 5000);
        }
    }
}
