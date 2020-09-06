package com.esi.conferencemanager.Controllers;

import com.esi.conferencemanager.Dto.UserRegistration;
import com.esi.conferencemanager.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    // sign-in - sign-up
    private final UserService userService ;
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }
    @ModelAttribute("user")
    public UserRegistration userRegistration(){return new UserRegistration();}
    @GetMapping("/registration")
    public String showForm(){
        return"registration";
    }
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") UserRegistration userRegistration){
        userService.registration(userRegistration);
        return "redirect:/auth/login"; }
    @GetMapping("/login")
    public String login(){return "login";}
}
