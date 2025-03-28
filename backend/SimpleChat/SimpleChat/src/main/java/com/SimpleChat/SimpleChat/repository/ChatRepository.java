package com.SimpleChat.SimpleChat.repository;

import com.SimpleChat.SimpleChat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    // Найти чат по имени
    Optional<Chat> findByName(String name);

    // Найти все чаты
    List<Chat> findAll();

    // Найти чаты, в которых есть хотя бы одно сообщение
    List<Chat> findByMessagesIsNotEmpty();
}



