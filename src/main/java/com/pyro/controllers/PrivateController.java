package com.pyro.controllers;

import com.pyro.repositories.MessageRepository;
import com.pyro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PrivateController {

    @Autowired
    UserService userService;

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/private")
    public String privateCab(Model model, Principal principal) {

        return "private";
    }

}
