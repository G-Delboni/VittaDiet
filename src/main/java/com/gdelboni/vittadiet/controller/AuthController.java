package com.gdelboni.vittadiet.controller;

import com.gdelboni.vittadiet.dto.LoginRequestDTO;
import com.gdelboni.vittadiet.dto.LoginResponseDTO;
import com.gdelboni.vittadiet.dto.RegisterRequestDTO;
import com.gdelboni.vittadiet.entities.User;
import com.gdelboni.vittadiet.enums.UserRole;
import com.gdelboni.vittadiet.infraestructure.service.TokenService;
import com.gdelboni.vittadiet.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        var userPassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        var authentication = authenticationManager.authenticate(userPassword);
        var token = tokenService.generateToken((User) Objects.requireNonNull(authentication.getPrincipal()));
        return ResponseEntity.ok(new LoginResponseDTO(token));
//                .cacheControl(CacheControl.noStore())
//                .body(new LoginResponseDTO("token"));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register (@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        if(this.userRepository.findByEmail(registerRequestDTO.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequestDTO.getPassword());
        User newUser = new User(registerRequestDTO.getEmail(), encryptedPassword, registerRequestDTO.getUserRole());
        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
