package com.example.mychatapp.message;

import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.web.socket.events.SessionConnectEvent;
import org.springframework.stereotype.Component;


@Component
@Log4j2
@WebListener
public class WebsocketRegistry implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof SessionDestroyedEvent) {
            SessionDestroyedEvent e = (SessionDestroyedEvent)event;
            log.info("Destroy Http Session: "+e.getSessionId());
        } else if (event instanceof SessionConnectEvent) {
            SessionConnectEvent e = (SessionConnectEvent)event;
            log.info("WebSocket Session Created: "+e.getWebSocketSession().getPrincipal().getName());
        }
    }
}
