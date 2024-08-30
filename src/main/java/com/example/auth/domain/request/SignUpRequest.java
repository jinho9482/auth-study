package com.example.auth.domain.request;

import com.example.auth.domain.entity.User;

import java.time.LocalDate;

public record SignUpRequest(
        String email,
        String password,
        String nickname,
        LocalDate birthday,
        String gender
) {
    public User toEntity(String encodedPassword) {
        return User.builder()
                .birthday(birthday)
                .email(email)
                .gender(gender)
                .password(encodedPassword)
                .nickname(nickname)
                .build();
    }
}
