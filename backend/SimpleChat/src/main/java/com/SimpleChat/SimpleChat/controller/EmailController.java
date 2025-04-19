package com.SimpleChat.SimpleChat.controller;

import com.SimpleChat.SimpleChat.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public String sendTestEmail(@RequestParam String to) {
        emailService.sendEmail(to, "Тестовое письмо", "Привет! Это тестовое сообщение из SimpleChat.");
        return "Письмо отправлено на " + to;
    }
}
