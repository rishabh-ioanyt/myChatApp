package com.example.mychatapp.message;

import com.example.mychatapp.auth.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

@Service
public class MessageService {

    DefaultSimpUserRegistry defaultSimpUserRegistry;

    MessageRepository messageRepository;

    UserRegistrationRepository userRegistrationRepository;


    @Autowired
    public MessageService(MessageRepository messageRepository, UserRegistrationRepository userRegistrationRepository,DefaultSimpUserRegistry defaultSimpUserRegistry) {
        this.messageRepository = messageRepository;
        this.userRegistrationRepository = userRegistrationRepository;
        this.defaultSimpUserRegistry = defaultSimpUserRegistry;
    }

    public void saveMessage(String message, String sendBy, String receivedBy) {
        Messages messages = new Messages();
        messages.setMessage(message);
        messages.setSendBy(userRegistrationRepository.findByUsername(sendBy));
        messages.setReceivedBy(userRegistrationRepository.findByUsername(receivedBy));
        messageRepository.save(messages);
    }

    public boolean checkUserOnline(String user){
        if (defaultSimpUserRegistry.getUser(user) !=null ){
            return true;
        }else {
            return false;
        }
    }
}
