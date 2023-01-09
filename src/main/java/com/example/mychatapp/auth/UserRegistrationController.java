package com.example.mychatapp.auth;

import com.example.mychatapp.message.MessageService;
import com.example.mychatapp.message.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class UserRegistrationController {

    UserRegistrationService userRegistrationService;

    MessageService messageService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService,MessageService messageService) {
        this.userRegistrationService = userRegistrationService;
        this.messageService =messageService;
    }

    @RequestMapping("/")
    public String defaultPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("UserDto", new UserDto());
            return "/signUp";
        }
        model.addAttribute("getAllUsers", userRegistrationService.getAllUsers());
        model.addAttribute("userDto", SecurityContextHolder.getContext().getAuthentication().getName());
        return "/chat";
    }

    @RequestMapping("/index")
    public String indexPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "/index";
        }
        model.addAttribute("getAllUsers", userRegistrationService.getAllUsers());
        model.addAttribute("userDto", SecurityContextHolder.getContext().getAuthentication().getName());
        return "/chat";
    }

    @RequestMapping("/chat")
    public String chat(Model model){
        model.addAttribute("getAllUsers", userRegistrationService.getAllUsers());
        model.addAttribute("userDto", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("onlineUsers", messageService.getAllOnlineUsers());
        return "/chat";
    }

    @PostMapping("/signUp")
    public String signUpPage(Model model,@ModelAttribute UserDto userDto) throws Exception {
        userRegistrationService.addUser(userDto);
        return "redirect:/index";
    }

    /*@RequestMapping("/logout")
    public String index(){
        return "/index";
    }*/
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
