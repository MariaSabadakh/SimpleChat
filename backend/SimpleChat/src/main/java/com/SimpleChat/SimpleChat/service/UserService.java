package com.SimpleChat.SimpleChat.service;

import com.SimpleChat.SimpleChat.model.User;
import com.SimpleChat.SimpleChat.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService; // Для отправки писем

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // Регистрация пользователя
    public boolean registerUser(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false;
        }

        String token = UUID.randomUUID().toString();
        User user = new User(username, email, passwordEncoder.encode(password), token, false);
        userRepository.save(user);

        // Отправка email с подтверждением
        String confirmLink = "http://localhost:8080/auth/confirm-email?token=" + token;
        emailService.sendEmail(email, "Confirm your email",
                "Click the link to confirm your email: " + confirmLink);

        return true;
    }

    // Подтверждение email
    public boolean confirmEmail(String token) {
        Optional<User> userOptional = userRepository.findByToken(token);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setConfirmed(true);
            user.setToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Аутентификация пользователя (с проверкой подтверждения email)
    public boolean authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return false;
        }

        if (!user.isConfirmed()) {
            throw new RuntimeException("Email is not confirmed! Check your inbox.");
        }

        return true;
    }
}
