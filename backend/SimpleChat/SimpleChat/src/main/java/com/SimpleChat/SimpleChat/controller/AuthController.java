package com.SimpleChat.SimpleChat.controller;

import com.SimpleChat.SimpleChat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        if (userService.registerUser(username, email, password)) {
            model.addAttribute("message", "Check your email to confirm your account!");
            return "register"; // Показываем страницу с сообщением
        } else {
            model.addAttribute("error", "User already exists!");
            return "register";
        }
    }

    @GetMapping("/confirm-email")
    public String confirmEmail(@RequestParam String token, Model model) {
        boolean isConfirmed = userService.confirmEmail(token);
        if (isConfirmed) {
            model.addAttribute("message", "Your email is confirmed! You can now log in.");
            return "login";
        } else {
            model.addAttribute("error", "Invalid or expired token!");
            return "register";
        }
    }
}