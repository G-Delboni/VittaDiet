package com.gdelboni.vittadiet.repository;

import com.gdelboni.vittadiet.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByUsername(String username);
}
