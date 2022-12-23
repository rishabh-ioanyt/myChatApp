package com.example.mychatapp.events;

import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Log4j2
@WebListener
public class SessionDisconnectEventListen implements ApplicationListener<SessionDisconnectEvent> {

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        log.info("message :" + new String(event.getMessage().getPayload()) + " principal:" + event.getUser().getName()+" close Status :"+ event.getCloseStatus().getReason());
    }
}
