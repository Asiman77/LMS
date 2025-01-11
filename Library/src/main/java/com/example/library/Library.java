package com.example.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
// Tətbiqi işə salan annotasiya
public class Library {

    public static void main(String[] args) {
        SpringApplication.run(Library.class, args); // Tətbiqi başlat
    }
}
