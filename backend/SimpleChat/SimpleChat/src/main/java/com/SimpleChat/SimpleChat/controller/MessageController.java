package com.SimpleChat.SimpleChat.controller;

import com.SimpleChat.SimpleChat.model.Message;
import com.SimpleChat.SimpleChat.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Controller
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Отображение сообщений в чате через HTML
    @GetMapping("/chat/{chatId}/messages")
    public String getMessagesByChat(@PathVariable Long chatId, Model model) {
        if (chatId == null || chatId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid chat ID");
        }

        List<Message> messages = messageService.getMessagesByChat(chatId);

        if (messages.isEmpty()) {
            model.addAttribute("noMessages", "No messages found in this chat");
        } else {
            model.addAttribute("messages", messages);
        }

        return "chat";  // Имя HTML-файла
    }
}
