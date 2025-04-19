package com.SimpleChat.SimpleChat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // login.html в папке templates
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // register.html в папке templates
    }
}