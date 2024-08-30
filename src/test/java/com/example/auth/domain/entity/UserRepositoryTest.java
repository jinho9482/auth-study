package com.example.auth.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() {
        String email = "wlshzz@naver.com";
        User user = User.builder().email(email).build();
        userRepository.save(user);

        Optional<User> byEmail = userRepository.findByEmail(email);

        assertTrue(byEmail.isPresent());
    }
}