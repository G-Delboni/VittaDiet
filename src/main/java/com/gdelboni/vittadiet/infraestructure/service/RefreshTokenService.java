package com.gdelboni.vittadiet.infraestructure.service;

import com.gdelboni.vittadiet.entities.RefreshToken;
import com.gdelboni.vittadiet.entities.User;
import com.gdelboni.vittadiet.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Objects;
import java.util.Optional;


@Service
public class RefreshTokenService {
    private static final Duration tokenDuration = Duration.ofDays(30);
    private final SecureRandom secureRandom = new SecureRandom();
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    private String hash(String raw) {
        try {
            byte[] d = MessageDigest.getInstance("SHA-256").digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(d);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    @Transactional
    public String create(User user){
        byte[] bytes = new  byte[32];
        secureRandom.nextBytes(bytes);
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setTokenHash(hash(rawToken));
        refreshToken.setExpirationDate(LocalDateTime.now().plus(tokenDuration));
        refreshTokenRepository.save(refreshToken);
        return rawToken;
    }

    @Transactional
    public Optional<User> consume(String rawToken){
        var stored = refreshTokenRepository.findByTokenHash(hash(rawToken));
        if(stored.isEmpty()){return Optional.empty();}
        var token = stored.get();
        if (token.isRevoked()){
            refreshTokenRepository.revokeAllByUser(token.getUser());
            return Optional.empty();
        }
        if(token.getExpirationDate().isBefore(LocalDateTime.now())){
            return Optional.empty();
        }
        token.setRevoked(true);
        return Optional.of(token.getUser());
    }

    @Transactional
    public void revoke(String raw) {
        refreshTokenRepository.findByTokenHash(hash(raw)).ifPresent(t -> t.setRevoked(true));
    }

}
