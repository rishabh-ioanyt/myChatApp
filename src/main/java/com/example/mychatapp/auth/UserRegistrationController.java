package com.example.mychatapp.auth;

import com.example.mychatapp.message.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserRegistrationController {

    UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/")
    public String indexPage(Model model){
        model.addAttribute("userDto", new UserDto());
        return "index";
    }

    @PostMapping("/login")
    public String registration(@ModelAttribute UserDto userDto, Model model) throws Exception {
        if (userRegistrationService.loginUser(userDto).equals("Logged Successfully")){
            model.addAttribute("getAllUsers", userRegistrationService.getAllUsers());
            model.addAttribute("userDto", userDto);
            return "chat";
        }else {
            return "index";
        }
    }
}
