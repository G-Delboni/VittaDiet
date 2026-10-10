package com.gdelboni.vittadiet.dto;

import com.gdelboni.vittadiet.enums.UserGender;
import com.gdelboni.vittadiet.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
    private Double height;
    private Double weight;
    private UserGender userGender;
}
