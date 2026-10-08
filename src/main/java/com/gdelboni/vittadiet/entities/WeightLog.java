package com.gdelboni.vittadiet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "weight_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeightLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Double weight;

    @CreationTimestamp
    private LocalDateTime loggedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
