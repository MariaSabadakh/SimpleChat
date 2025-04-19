package com.SimpleChat.SimpleChat.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // Название чата

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages; // Сообщения в этом чате

    public Chat() {}

    public Chat(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
