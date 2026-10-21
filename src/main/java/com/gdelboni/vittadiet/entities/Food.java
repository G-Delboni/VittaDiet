package com.gdelboni.vittadiet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NullMarked;

@Table(name = "food")
@Entity(name = "food")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NullMarked
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Integer tacoCode;
    private String name;
    private Double caloriePerHundredGrams;
    private Double fatPerHundredGrams;
    private Double carbohydratePerHundredGrams;
    private Double proteinPerHundredGrams;

    public Nutrients nutrientsPerHundredGrams() {
        return Nutrients.of(caloriePerHundredGrams, proteinPerHundredGrams,
                carbohydratePerHundredGrams, fatPerHundredGrams);
    }
}
