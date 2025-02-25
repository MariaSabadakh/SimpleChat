package com.SimpleChat.SimpleChat.service;
// Создает нового пользователя.Проверяет, существует ли уже такой пользователь.
import com.SimpleChat.SimpleChat.model.User;
import com.SimpleChat.SimpleChat.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
//    public Optional<User> findByUsername(String username) {
//        Optional<User> user = userRepository.findByUsername(username);
//        user.ifPresent(u -> System.out.println("Пароль из БД: " + u.getPassword()));
//        return user;
//    }


//    public Optional<User> findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }

    public boolean registerUser(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
            return false; // Если пользователь уже существует
        }

        User user = new User(username, email, passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }
}

