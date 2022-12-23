package com.example.mychatapp.buffermessage;

import com.example.mychatapp.auth.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BufferMessagesService {

    BufferMessagesRepository bufferMessagesRepository;

    UserRegistrationRepository userRegistrationRepository;

    @Autowired
    public BufferMessagesService(BufferMessagesRepository bufferMessagesRepository, UserRegistrationRepository userRegistrationRepository) {
        this.bufferMessagesRepository = bufferMessagesRepository;
        this.userRegistrationRepository = userRegistrationRepository;
    }

    public void saveBufferMessages(String message, String sendBy, String receivedBy){
        BufferMessages bufferMessages = new BufferMessages();
        bufferMessages.setMessage(message);
        bufferMessages.setSendBy(userRegistrationRepository.findByUsername(sendBy));
        bufferMessages.setReceivedBy(userRegistrationRepository.findByUsername(receivedBy));
        bufferMessagesRepository.save(bufferMessages);
    }

}
