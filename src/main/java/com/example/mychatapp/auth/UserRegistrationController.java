package com.example.mychatapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserRegistrationController {

    UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @RequestMapping("/index")
    public String indexPage(){
        //model.addAttribute("userDto", new UserDto());
        return "/index";
    }

    @RequestMapping("/chat")
    public String chat(Model model){
        model.addAttribute("getAllUsers", userRegistrationService.getAllUsers());
        model.addAttribute("userDto", SecurityContextHolder.getContext().getAuthentication().getName());
        return "/chat";
    }
  /*  @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "index";
    }
*/
    /*@PostMapping("/login")
    public String registration(@ModelAttribute UserDto userDto, Model model) throws Exception {
        if (userRegistrationService.loginUser(userDto).equals("Logged Successfully")){
            model.addAttribute("getAllUsers", userRegistrationService.getAllUsers());
            model.addAttribute("userDto", userDto);
            return "chat";
        }else {
            return "index";
        }
    }*/
}
