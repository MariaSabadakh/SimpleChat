package com.SimpleChat.SimpleChat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatPageController {
    @GetMapping
    public String chatPage() {
        return "chat"; // Файл должен быть в src/main/resources/templates/chat.html
    }
}
