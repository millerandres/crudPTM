package com.crudptm.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Necesario para APIs REST
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/**").permitAll() // Permite todas las rutas bajo /api/
                        .anyRequest().permitAll()               // Opcional: permite todo
                );
        return http.build();
    }
}
