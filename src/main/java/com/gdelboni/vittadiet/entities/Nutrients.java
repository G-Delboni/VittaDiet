package com.gdelboni.vittadiet.entities;

public record Nutrients(double calories, double protein, double carbo, double fat) {
    public static final Nutrients ZERO = new Nutrients(0, 0, 0, 0);

    public Nutrients plus (Nutrients otherNutrients) {
        return new Nutrients(
                calories + otherNutrients.calories,
                protein + otherNutrients.protein,
                carbo + otherNutrients.carbo,
                fat + otherNutrients.fat
        );
    }

    public Nutrients times (Double quantityInGrams) {
        return new Nutrients(
                calories * quantityInGrams,
                protein * quantityInGrams,
                carbo * quantityInGrams,
                fat * quantityInGrams
        );
    }

}
