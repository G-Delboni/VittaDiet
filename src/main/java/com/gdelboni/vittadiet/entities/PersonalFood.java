package com.gdelboni.vittadiet.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "personal_food")
@Getter
@Setter
@NoArgsConstructor
public class PersonalFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private Double caloriePerHundredGrams;
    private Double proteinPerHundredGrams;
    private Double carbohydratePerHundredGrams;
    private Double fatPerHundredGrams;

    public Nutrients nutrientsPerHundredGrams() {
        return new Nutrients(caloriePerHundredGrams, proteinPerHundredGrams,
                carbohydratePerHundredGrams, fatPerHundredGrams);
    }
}
