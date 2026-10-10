package com.gdelboni.vittadiet.dto;

import com.gdelboni.vittadiet.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String email;
    private String password;
}
