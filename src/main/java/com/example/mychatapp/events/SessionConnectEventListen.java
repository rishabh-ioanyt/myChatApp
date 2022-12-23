package com.example.mychatapp.events;

import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.session.web.socket.events.SessionConnectEvent;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@WebListener
public class SessionConnectEventListen implements ApplicationListener<SessionConnectEvent> {
    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        log.info("WebSocket Session Created by: "+event.getWebSocketSession().getPrincipal().getName()+" with Session Id :"+ event.getWebSocketSession().getId());
    }
}
