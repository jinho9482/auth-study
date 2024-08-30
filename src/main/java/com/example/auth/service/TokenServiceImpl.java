package com.example.auth.service;

import com.example.auth.domain.entity.Team;
import com.example.auth.domain.entity.TeamRepository;
import com.example.auth.domain.request.TeamRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TeamRepository teamRepository;
    @Override
    public boolean isAuthenticatedTeam(TeamRequest request) {
        Optional<Team> byLeaderAndSecret = teamRepository.findByLeaderAndSecret(
                request.leader(), request.secret()
        );
        byLeaderAndSecret.orElseThrow(IllegalArgumentException::new);
        return true;
    }
}
