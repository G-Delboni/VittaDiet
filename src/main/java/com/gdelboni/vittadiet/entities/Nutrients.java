package com.gdelboni.vittadiet.entities;

import java.util.Objects;

public record Nutrients(double calories, double protein, double carbo, double fat) {
    public static final Nutrients ZERO = new Nutrients(0, 0, 0, 0);


    public static Nutrients of(Double calories, Double protein, Double carbo, Double fat) {
        return new Nutrients(
                Objects.requireNonNull(calories, "calories is required"),
                protein == null ? 0 : protein,
                carbo == null ? 0 : carbo,
                fat == null ? 0 : fat);
    }

    public Nutrients plus (Nutrients otherNutrients) {
        return new Nutrients(
                calories + otherNutrients.calories,
                protein + otherNutrients.protein,
                carbo + otherNutrients.carbo,
                fat + otherNutrients.fat
        );
    }

    public Nutrients times(double factor) {
        return new Nutrients(calories * factor, protein * factor, carbo * factor, fat * factor);
    }

}
