package com.gdelboni.vittadiet.entities;

public class Food {
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
