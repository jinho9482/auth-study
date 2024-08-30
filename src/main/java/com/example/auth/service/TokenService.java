package com.example.auth.service;

import com.example.auth.domain.entity.TeamRepository;
import com.example.auth.domain.request.TeamRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface TokenService {
    boolean isAuthenticatedTeam(TeamRequest request);
}
