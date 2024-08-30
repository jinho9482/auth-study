package com.example.auth.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "TEAMS")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // auto increment 가 아님
    @Column(name = "TEAM_ID")
    private UUID id;

    @Column(name = "TEAM_LEADER", unique = true)
    private String leader;

    @Column(name = "TEAM_PASSWORD")
    private String secret;
}

// 요청 보낼 때 : api/v1/auth/token/parse
// method : post
// header Authorization: : Bearer ~~
// body : { "leader" : "조진호", "secret" : "7984" }

