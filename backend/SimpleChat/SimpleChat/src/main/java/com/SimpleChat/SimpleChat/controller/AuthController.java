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
            return "redirect:/login"; // Перенаправление на вход после успешной регистрации
        } else {
            model.addAttribute("error", "User already exists!");
            return "register"; // Оставляем пользователя на странице регистрации с ошибкой
        }
    }
}