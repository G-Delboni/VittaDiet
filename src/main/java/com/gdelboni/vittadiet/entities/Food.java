package com.gdelboni.vittadiet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class Food {
    @Id
    private Long id;
    @Column(unique = true)
    private String tacoCode;
    private String name;
    private Double quantityInGrams;
    private Double caloriePerHundredGrams;
    private Double fatPerHundredGrams;
    private Double carbohydratePerHundredGrams;
    private Double proteinPerHundredGrams;

    public Food(String name, Double quantityInGrams) {
        this.name = name;
        this.quantityInGrams = quantityInGrams;
    }


}
