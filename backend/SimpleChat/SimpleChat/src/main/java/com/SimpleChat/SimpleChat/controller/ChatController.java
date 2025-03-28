package com.SimpleChat.SimpleChat.controller;

import com.SimpleChat.SimpleChat.model.Chat;
import com.SimpleChat.SimpleChat.model.Message;
import com.SimpleChat.SimpleChat.model.User;
import com.SimpleChat.SimpleChat.repository.ChatRepository;
import com.SimpleChat.SimpleChat.repository.MessageRepository;
import com.SimpleChat.SimpleChat.repository.UserRepository;
import com.SimpleChat.SimpleChat.service.MessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final MessageService messageService;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    public ChatController(MessageService messageService,
                          UserRepository userRepository,
                          ChatRepository chatRepository,
                          MessageRepository messageRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/chat")
    public String chatPage(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            log.info("Username в контроллере: {}", username);
            model.addAttribute("username", username); // добавляет имя в модель
        } else {
            log.warn("Principal == null, пользователь не аутентифицирован");
        }
        return "chat";
    }

    @MessageMapping("/chat/{chatId}/send")
    @SendTo("/topic/chat/{chatId}")
    public Message sendMessage(@Payload Message message, @DestinationVariable Long chatId, Principal principal) {
        log.info("Received message in chat {} from {}: {}", chatId, principal.getName(), message.getContent());

        // Находим пользователя по логину
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + principal.getName()));

        // Находим чат по ID
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found: " + chatId));

        // Привязываем сообщение к пользователю и чату
        message.setUser(user);
        message.setChat(chat);

        // Сохраняем сообщение в базу
        messageRepository.save(message);

        return message;
    }

    // Добавление пользователя в сессию
    @MessageMapping("/addUser")
    @SendTo("/topic/chat")
    public Message addUser(Message message, SimpMessageHeaderAccessor headerAccessor) {
        if (message == null || message.getUser() == null || message.getUser().getUsername() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is missing!");
        }

        String username = message.getUser().getUsername();
        headerAccessor.getSessionAttributes().put("username", username); // Сохраняет имя пользователя в сессии
        return message;
    }
}