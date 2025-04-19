package com.SimpleChat.SimpleChat.controller;

import com.SimpleChat.SimpleChat.model.Message;
import com.SimpleChat.SimpleChat.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // ✅ Получение всех сообщений в чате (через API)
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessagesByChat1(@PathVariable Long chatId) {
        if (chatId == null || chatId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        List<Message> messages = messageService.getMessagesByChat(chatId);

        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(messages);
    }

    // ✅ Получение одного сообщения по ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Создание нового сообщения
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message savedMessage = messageService.saveMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

    // ✅ Удаление сообщения по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        if (messageService.deleteMessage(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ HTML-отображение сообщений (через Thymeleaf)
    @GetMapping("/chat/{chatId}/view")
    public String viewMessagesByChat(@PathVariable Long chatId, Model model) {
        List<Message> messages = messageService.getMessagesByChat(chatId);
        model.addAttribute("messages", messages.isEmpty() ? null : messages);
        return "chat";  // HTML-шаблон
    }

    @RestController
    @RequestMapping("/messages")
    public class MessageController1 {

        @PostMapping
        public ResponseEntity<String> createMessage(@RequestBody String message) {
            return ResponseEntity.ok("Message received: " + message);
        }
    }


}
