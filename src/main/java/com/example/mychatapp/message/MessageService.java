package com.example.mychatapp.message;

import com.example.mychatapp.auth.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    MessageRepository messageRepository;

    UserRegistrationRepository userRegistrationRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRegistrationRepository userRegistrationRepository) {
        this.messageRepository = messageRepository;
        this.userRegistrationRepository = userRegistrationRepository;
    }

    public void saveMessage(String message, String sendBy, String receivedBy) {
        Messages messages = new Messages();
        messages.setMessage(message);
        messages.setSendBy(userRegistrationRepository.findByUsername(sendBy));
        messages.setReceivedBy(userRegistrationRepository.findByUsername(receivedBy));
        messageRepository.save(messages);
    }
}
