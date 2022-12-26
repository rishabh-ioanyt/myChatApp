package com.example.mychatapp.message;

import com.example.mychatapp.auth.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

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

    List<SseEmitter> sseEmitterList = new CopyOnWriteArrayList<>();

    public List<String> getAllOnlineUsers(){
        return defaultSimpUserRegistry.getUsers().stream().map(SimpUser::getName).collect(Collectors.toList());
    }

    public void sendSSE(){
        sseEmitterList.forEach(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().name("spring").data("hello"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public SseEmitter addUser() {
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitterList.add(sseEmitter);
        System.out.println("sseEmitter added");
        sseEmitter.onCompletion(() -> sseEmitterList.remove(sseEmitter));
        return sseEmitter;
    }
}
