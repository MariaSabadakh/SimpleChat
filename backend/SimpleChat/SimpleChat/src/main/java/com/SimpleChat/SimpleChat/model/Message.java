package com.SimpleChat.SimpleChat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    public Message() {
        this.timestamp = LocalDateTime.now(); // Устанавливаем дату сразу
    }

    public Message(Chat chat, User user, String content) {
        this();
        setChat(chat);
        setUser(user);
        setContent(content);
    }

    public Long getId() {
        return id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        if (chat == null) {
            throw new IllegalArgumentException("Chat cannot be null");
        }
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be empty");
        }
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public String getSender() {
        return user.getUsername(); //  Берем имя пользователя из user
    }

}
