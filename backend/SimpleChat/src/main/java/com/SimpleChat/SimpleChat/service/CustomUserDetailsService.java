package com.SimpleChat.SimpleChat.service;

import com.SimpleChat.SimpleChat.model.User;
import com.SimpleChat.SimpleChat.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Добавили

    // Конструктор с двумя параметрами
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Присвоили
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        System.out.println("Пользователь найден: " + user.getUsername());
        System.out.println("Пароль (захешированный): " + user.getPassword());

        System.out.println("=== [DEBUG: UserDetailsService] ===");
        System.out.println("Пользователь найден: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Роль из БД: [" + user.getRole() + "]");
        System.out.println("Пароль (захешированный): " + user.getPassword());
        System.out.println("=== [END DEBUG] ===");


        // Проверка пароля
        //String inputPassword = "test1"; // Здесь надо подставить реальный вводимый пароль
        //System.out.println("Введенный пароль: " + inputPassword);
        //System.out.println("Пароль из БД: " + user.getPassword());
        //System.out.println("Проверка совпадения паролей: " + passwordEncoder.matches(inputPassword, user.getPassword()));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // Пароль уже зашифрован (BCrypt)
                .roles(user.getRole()) // Можно передавать роли из БД
                .build();
    }


}
