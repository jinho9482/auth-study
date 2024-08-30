package com.example.auth.domain.request;

import com.example.auth.domain.entity.Team;

public record TeamRequest(
        String leader,
        String secret
) {
    public Team toEntity() {
        return Team.builder()
                .leader(leader)
                .secret(secret)
                .build();
    }
}
