package com.example.auth.global.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest // spring에 있는 변수들을 사용하기 위해 이 annotation을 사용, spring이 붙으면 느림
class JwtUtilTest {
    // 이렇게 분리해놓으면 훨씬 빠르다.

    private final String secret = "12jdasoifjpoij39205u23t20981fasdvsdfg52323";

    private final JwtUtil jwtUtil = new JwtUtil(secret,500L);

    @Test
    void generateToken() {
        // given
        String email = "wlshzz@naver.com";

        // when
        String token = jwtUtil.generateToken(email);

        // then
        assertNotNull(token);
        assertEquals(3, token.split("\\.").length);
        System.out.println(token);

    }

    @Nested
    class getByEmailFromTokenAndValidate {
        @Test
        void success() {
            String email = "wlshzz@naver.com";
            String token = jwtUtil.generateToken(email);
            String answer = jwtUtil.getEmailFromTokenAndValidate(token);
            assertNotNull(answer);
            assertEquals(email, answer);
        }

        @Test
        void expired() throws InterruptedException {
            String email = "wlshzz@naver.com";
            String token = jwtUtil.generateToken(email);
            Thread.sleep(1000);
            assertThrows(JwtException.class, () -> jwtUtil.getEmailFromTokenAndValidate(token));
        }
    }
}