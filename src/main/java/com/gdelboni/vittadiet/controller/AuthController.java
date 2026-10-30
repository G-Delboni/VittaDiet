package com.gdelboni.vittadiet.controller;

import com.gdelboni.vittadiet.dto.LoginRequestDTO;
import com.gdelboni.vittadiet.dto.LoginResponseDTO;
import com.gdelboni.vittadiet.dto.RegisterRequestDTO;
import com.gdelboni.vittadiet.entities.User;
import com.gdelboni.vittadiet.infraestructure.service.RefreshTokenService;
import com.gdelboni.vittadiet.infraestructure.service.TokenService;
import com.gdelboni.vittadiet.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Value("${app.allowed-origin}")
    private String allowedOrigin;

    // Sem Origin (curl, app nativo) passa; com Origin, só a do front permitido.
    private boolean originAllowed(String origin) {
        return origin == null || origin.equals(allowedOrigin);
    }

    private ResponseCookie refreshCookie(String value, Duration maxAge) {
        return ResponseCookie.from("refresh_token", value)
                .httpOnly(true).secure(true).sameSite("Strict")
                .path("/api/auth").maxAge(maxAge).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        var userPassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        var authentication = authenticationManager.authenticate(userPassword);
        var user = (User) Objects.requireNonNull(authentication.getPrincipal());

        var access = tokenService.generateToken(user);
        var refresh = refreshTokenService.create(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie(refresh, Duration.ofDays(30)).toString())
                .cacheControl(CacheControl.noStore())
                .body(new LoginResponseDTO(access, user.getUsername()));
    }


    @PostMapping("/register")
    public ResponseEntity<Void> register (@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        if(this.userRepository.findByEmail(registerRequestDTO.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();   // 409: e-mail já cadastrado
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequestDTO.getPassword());
        User newUser = new User(registerRequestDTO.getUsername(), registerRequestDTO.getEmail(), encryptedPassword, registerRequestDTO.getHeight(), registerRequestDTO.getUserGender());
        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(
            @CookieValue(name = "refresh_token", required = false) String raw,
            @RequestHeader(value = "Origin", required = false) String origin) {
        if (!originAllowed(origin)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Optional<User> user = (raw == null) ? Optional.empty() : refreshTokenService.consume(raw);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.SET_COOKIE, refreshCookie("", Duration.ZERO).toString())
                    .build();
        }
        var access = tokenService.generateToken(user.get());
        var newRefresh = refreshTokenService.create(user.get());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie(newRefresh, Duration.ofDays(30)).toString())
                .cacheControl(CacheControl.noStore())
                .body(new LoginResponseDTO(access, user.get().getUsername()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = "refresh_token", required = false) String raw,
            @RequestHeader(value = "Origin", required = false) String origin){
        if (!originAllowed(origin)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if (raw != null) refreshTokenService.revoke(raw);
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, refreshCookie("", Duration.ZERO).toString())
                .build();
    }
}
