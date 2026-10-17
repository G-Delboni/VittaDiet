package com.gdelboni.vittadiet.dto;

import com.gdelboni.vittadiet.enums.UserGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    @NotBlank
    private String username;
    @NotBlank @Email
    private String email;
    @NotBlank @Size(min = 8)
    private String password;
    @NotNull @Positive            // users.height é NOT NULL
    private Double height;
    private Double weight;
    @NotNull                      // users.gender é NOT NULL
    private UserGender userGender;
}
