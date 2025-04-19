package com.SimpleChat.SimpleChat.controller;

import com.SimpleChat.SimpleChat.model.Message;
import com.SimpleChat.SimpleChat.model.User;
import com.SimpleChat.SimpleChat.repository.UserRepository;
import com.SimpleChat.SimpleChat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    // Страница админки (возвращает admin.html)
    @GetMapping
    public String getAdminPage(Model model) {
        // Загружаем все сообщения и пользователей
        List<Message> messages = messageService.getAllMessages();
        List<User> users = userRepository.findAll();

        model.addAttribute("messages", messages);
        model.addAttribute("users", users);

        return "admin";  // Возвращаем HTML страницу admin.html
    }

    // REST API: получить все сообщения (используется из JS)
    @ResponseBody
    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    // REST API: удалить сообщение по ID (используется из JS)
    @ResponseBody
    @DeleteMapping("/messages/{id}")
    public String deleteMessage(@PathVariable Long id) {
        boolean deleted = messageService.deleteMessageById(id);
        return deleted ? "Message deleted" : "Message not found";
    }

    // 2. Получить всех пользователей
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";  // Возвращаем HTML страницу admin.html
    }
}
