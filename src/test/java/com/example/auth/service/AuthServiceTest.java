package com.example.auth.service;

import com.example.auth.exception.ExistingUserException;
import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.SignInRequest;
import com.example.auth.domain.request.SignUpRequest;
import com.example.auth.domain.response.SignInResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService; // impl에 @Service가 없으면 bean 에 등록이 안되어있기 때문에 에러가 뜬다.

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Nested
    class signIn {
        @Test
        void success() {
            // given
            User init = User.builder().email("wlshzz@naver.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("wlshzz")
                    .gender("male")
                    .birthday(LocalDate.of(1994, 6, 20))
                    .build();
            userRepository.save(init);
            SignInRequest request = new SignInRequest("wlshzz@naver.com", "1234");
            // when
            SignInResponse res = authService.signIn(request);
            // then
            assertNotNull(res.token());
            assertEquals(3, res.token().split("\\.").length);
            assertEquals("Bearer", res.tokenType());
        }

        @Test
        void userNotFoundFail() {
            // given
            User init = User.builder().email("wlshzz@naver.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("wlshzz")
                    .gender("male")
                    .birthday(LocalDate.of(1994, 6, 20))
                    .build();
            userRepository.save(init);
            SignInRequest request = new SignInRequest("jinho@naver.com", "1234");

            // when & then
            assertThrows(ExistingUserException.class, () -> authService.signIn(request));
        }

        @Test
        void passwordFail() {
            // given
            User init = User.builder().email("wlshzz@naver.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("wlshzz")
                    .gender("male")
                    .birthday(LocalDate.of(1994, 6, 20))
                    .build();
            userRepository.save(init);
            SignInRequest request = new SignInRequest("wlshzz@naver.com", "12549849");

            // when & then
            assertThrows(IllegalArgumentException.class, () -> authService.signIn(request));
        }

    }





    @Nested // 하나로 묶어서 테스트하는 것
    class signUp {
        @Test
        void success() {
            // given
            SignUpRequest request = new SignUpRequest("wlshzz@naver.com",
                    "1234",
                    "머리좀긴크리링",
                    LocalDate.of(2024, 5, 8),
                    "male"
            );

            // when
            authService.insertUser(request);
            // then
            Optional<User> byEmail = userRepository.findByEmail(request.email());
            assertTrue(byEmail.isPresent());
            assertNotEquals("1234", byEmail.get().getEmail());
        }

        @Test
        void emailAlreadyExist() {
            // given
            SignUpRequest request = new SignUpRequest("jinho@naver.com",
                    "1234",
                    "머리좀긴크리링",
                    LocalDate.of(2024, 5, 8),
                    "male"
            );

//            userRepository.save(User.builder().email("jinho2@naver.com").build());
            // when & then (데이터를 넣고 에러가 즉각 발생하기 때문에 같이 쓴다.)
            authService.insertUser(request);
            assertThrows(ExistingUserException.class, () ->
                authService.insertUser(request)
            );

        }
    }
}