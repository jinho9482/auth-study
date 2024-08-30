package com.example.auth.service;

import com.example.auth.exception.ExistingUserException;
import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.SignInRequest;
import com.example.auth.domain.request.SignUpRequest;
import com.example.auth.domain.response.SignInResponse;
import com.example.auth.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // new BCryptPasswordEncoder() 와 같다.
    private final JwtUtil jwtUtil;



    @Override
//    @Transactional
    public void insertUser(SignUpRequest signUpRequest) {
        log.info("insert user");
        // 1. 유저가 있는지 찾아보고.
        Optional<User> byId = userRepository.findByEmail(signUpRequest.email());
        // 2-1 있으면 에러
        if (byId.isPresent()) throw new ExistingUserException(signUpRequest.email());
        // 2-2 없으면 insert
        String encodedPassword = passwordEncoder.encode(signUpRequest.password());
        User user = signUpRequest.toEntity(encodedPassword);
        log.info(signUpRequest.toString());
        // error > info > debug > trace ? 갈수록 하위레벨
        userRepository.save(user);
    }


    @Override
    public SignInResponse signIn(SignInRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.email());
        if (byEmail.isEmpty()) throw new ExistingUserException(request.email());
        if (!passwordEncoder.matches(request.password(), byEmail.get().getPassword()))
            throw new IllegalArgumentException("비밀번호를 확인해주세요");
        String token = jwtUtil.generateToken(request.email());
        SignInResponse signInResponse = SignInResponse.from(token);
        return signInResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
