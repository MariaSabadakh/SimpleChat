package com.SimpleChat.SimpleChat.service;
//получать список всех чатов, находить конкретный чат по ID и создавать новые чаты
import com.SimpleChat.SimpleChat.model.Chat;
import com.SimpleChat.SimpleChat.repository.ChatRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Optional<Chat> getChatById(Long id) {
        return chatRepository.findById(id);
    }

    public Chat createChat(String name) {
        Chat chat = new Chat(name);
        return chatRepository.save(chat);
    }
}
