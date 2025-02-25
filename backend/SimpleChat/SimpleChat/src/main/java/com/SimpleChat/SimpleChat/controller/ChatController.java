package com.SimpleChat.SimpleChat.controller;

import com.SimpleChat.SimpleChat.model.Message;
import com.SimpleChat.SimpleChat.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/chat")
    public Message sendMessage(Message message, SimpMessageHeaderAccessor headerAccessor) {
        // Получаем имя пользователя из WebSocket-сессии
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username == null) {
            throw new RuntimeException("User not authenticated");
        }

        // Проверяем, есть ли у сообщения связанный чат
        if (message.getChat() == null || message.getChat().getId() == null) {
            throw new RuntimeException("Chat ID is missing!");
        }

        // Проверяем, есть ли у сообщения связанный пользователь
        if (message.getUser() == null || message.getUser().getId() == null) {
            throw new RuntimeException("User ID is missing!");
        }

        // Устанавливаем имя отправителя
        message.setSenderName(username);

        // Сохраняем сообщение в БД
        return messageService.sendMessage(
                message.getChat().getId(),
                message.getUser().getId(),
                message.getContent()
        );
    }
}
