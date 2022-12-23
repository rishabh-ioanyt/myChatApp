package com.example.mychatapp.events;

import com.example.mychatapp.auth.UserRegistration;
import com.example.mychatapp.auth.UserRegistrationRepository;
import com.example.mychatapp.auth.UserRegistrationService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class ApplicationListen implements ApplicationListener<ApplicationReadyEvent> {

    UserRegistrationRepository registrationRepository;

    UserRegistrationService userRegistrationService;

    @Autowired
    public ApplicationListen(UserRegistrationRepository registrationRepository, UserRegistrationService userRegistrationService) {
        this.registrationRepository = registrationRepository;
        this.userRegistrationService = userRegistrationService;
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        if (registrationRepository.count() == 0){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            UserRegistration userRegistration = new UserRegistration();
            userRegistration.setUsername("rishabh");
            userRegistration.setPassword(passwordEncoder.encode("rishabh"));
            registrationRepository.save(userRegistration);

            UserRegistration secondUser = new UserRegistration();
            secondUser.setUsername("aashish");
            secondUser.setPassword(passwordEncoder.encode("aashish"));
            registrationRepository.save(secondUser);

            UserRegistration thirdUser = new UserRegistration();
            thirdUser.setUsername("ioanyt");
            thirdUser.setPassword(passwordEncoder.encode("ioanyt"));
            registrationRepository.save(thirdUser);

            UserRegistration adminUser = new UserRegistration();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            registrationRepository.save(adminUser);
        }
    }
}
