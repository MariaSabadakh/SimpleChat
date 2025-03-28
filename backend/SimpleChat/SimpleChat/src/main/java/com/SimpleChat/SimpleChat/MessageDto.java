package com.SimpleChat.SimpleChat;
// Отвечает за структуру данных, которые передаются в WebSocket
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MessageDto {
    @NotNull
    private Long chatId;

    @NotNull
    private Long userId;

    @NotBlank
    private String content;

    // Геттеры и сеттеры
    public Long getChatId() { return chatId; }
    public void setChatId(Long chatId) { this.chatId = chatId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
