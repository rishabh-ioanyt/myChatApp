package com.example.mychatapp.events;

import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@Log4j2
@WebListener
public class SessionSubscribeEventListen implements ApplicationListener<SessionSubscribeEvent> {

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        String s = new String(event.getMessage().getPayload());
        log.info("principal :"+event.getUser().getName()+" subscribe topic "+s);
    }
}
