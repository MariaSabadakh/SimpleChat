package com.SimpleChat.SimpleChat.config;
//Настраивает Spring Security (защита, аутентификация, авторизация).
import com.SimpleChat.SimpleChat.repository.UserRepository;
import com.SimpleChat.SimpleChat.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF
                //.anonymous(withDefaults()) // Включаем анонимный доступ
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login", "/ws/**").permitAll() // Добавили "/ws/**"
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ⬅ Ограничили доступ
                        .anyRequest().authenticated()
                )



                .formLogin(login -> login
                        .loginPage("/auth/login") // Указываем кастомную страницу логина
                        .loginProcessingUrl("/auth/login") // Обработка логина
                        .defaultSuccessUrl("/chat", true) // После входа отправляет в /chats
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "GET")) // Разрешаем GET-запросы на logout
                        .logoutSuccessUrl("/auth/login")
                        .permitAll()
                );

        return http.build();
    }


}


