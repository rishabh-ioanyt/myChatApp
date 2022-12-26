package com.example.mychatapp.events;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2 @WebListener
public class HttpConfig {

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {
                log.info("new session Created - "+se.getSession().getId());
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent se) {
                log.info(" session Destroyed - "+se.getSession().getId());
            }
        };
    }
}
