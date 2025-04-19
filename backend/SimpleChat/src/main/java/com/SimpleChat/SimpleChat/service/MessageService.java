package com.SimpleChat.SimpleChat.service;

import com.SimpleChat.SimpleChat.model.Chat;
import com.SimpleChat.SimpleChat.model.Message;
import com.SimpleChat.SimpleChat.model.User;
import com.SimpleChat.SimpleChat.repository.ChatRepository;
import com.SimpleChat.SimpleChat.repository.MessageRepository;
import com.SimpleChat.SimpleChat.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public List<Message> getMessagesByChat(Long chatId) {
        return messageRepository.findByChatIdOrderByTimestampAsc(chatId);
    }


    public Message sendMessage(Long chatId, Long userId, String content) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = new Message(chat, user, content); // Передаем senderName
        return messageRepository.save(message);
    }

    // ✅ Получение всех сообщений чата
    public List<Message> getMessagesByChat1(Long chatId) {
        return messageRepository.findByChatId(chatId);
    }

    // ✅ Получение одного сообщения по ID
    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    // ✅ Сохранение (создание) сообщения
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    // ✅ Удаление сообщения по ID
    public boolean deleteMessage(Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean createMessage(Message message) {
        // Реализация сохранения сообщения
        return true; // или возвращай результат операции
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public boolean deleteMessageById(Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

}