package com.gdelboni.vittadiet.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false) @JoinColumn(name="user_id")
    private User user;
    private String tokenHash;
    private LocalDateTime expirationDate;
    private boolean revoked;   // o Lombok gera isRevoked() e setRevoked()
}
