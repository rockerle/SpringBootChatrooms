package com.example.chatserver.controller;

import com.example.chatserver.dto.MongoUser;
import com.example.chatserver.service.AuthUserDetailsService;
import com.example.chatserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class RegisterController {

    AuthUserDetailsService auds;
    UserService us;
    private final Logger logger = Logger.getLogger("RC-LOGGER");

    @Autowired
    public RegisterController(AuthUserDetailsService auds, UserService us){
        this.auds = auds;
        this.us = us;
    }
    @GetMapping("/register")
    public String showForm(Model model){
            MongoUser user = new MongoUser();
            model.addAttribute("user",user);
            return "registerUser";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") MongoUser user){
        this.logger.info("registered user "+user.toString());
        user.setRoles(List.of("USER"));
        if(us.addUser(user))
            return "index";
        else return "regFailed";
    }
}
