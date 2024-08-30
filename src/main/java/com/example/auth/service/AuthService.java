package com.example.auth.service;

import com.example.auth.domain.request.SignInRequest;
import com.example.auth.domain.request.SignUpRequest;
import com.example.auth.domain.response.SignInResponse;

public interface AuthService {
    void insertUser(SignUpRequest signUpRequest);

    SignInResponse signIn(SignInRequest request);
}
