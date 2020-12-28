package com.example.userauthenticationjwt.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionsController {    
    @GetMapping("/login")
    public String showSignInForm() {
        return "sign-in";
    }
}
