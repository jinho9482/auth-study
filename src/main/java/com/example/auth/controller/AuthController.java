package com.example.auth.controller;

import com.example.auth.domain.request.SignInRequest;
import com.example.auth.domain.request.SignUpRequest;
import com.example.auth.domain.request.TeamRequest;
import com.example.auth.domain.response.SignInResponse;
import com.example.auth.domain.response.UserResponse;
import com.example.auth.service.AuthService;
import com.example.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auths")
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody SignUpRequest req) {
        authService.insertUser(req);
    }

    @PostMapping("/signin")
    public SignInResponse signIn(@RequestBody SignInRequest req) {
        return authService.signIn(req);
    }

    @PostMapping("/token")
    public UserResponse getUserResponse(@RequestBody TeamRequest request) {
        tokenService.isAuthenticatedTeam(request);
        return null;
    }
}
