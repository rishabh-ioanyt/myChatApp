package com.example.mychatapp.events;

import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Log4j2
@WebListener
@Component
public class SessionUnsubscribeEventListen implements ApplicationListener<SessionUnsubscribeEvent> {
    @Override
    public void onApplicationEvent(SessionUnsubscribeEvent event) {
        log.info("Principal : "+event.getUser()+" Unsubscribe Topic :"+new String(event.getMessage().getPayload()));
    }
}
