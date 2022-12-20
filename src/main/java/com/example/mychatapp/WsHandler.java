package com.example.mychatapp;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.socket.*;

@Log4j2
public class WsHandler implements WebSocketHandler {



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("welcome " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        session.sendMessage(message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
