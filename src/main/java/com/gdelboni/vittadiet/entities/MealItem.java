package com.gdelboni.vittadiet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Meal meal;
    @ManyToOne
    private Food food;
    @ManyToOne
    private PersonalFood personalFood;
    private Double quantityInGrams;

    @PrePersist
    @PreUpdate
    private void validateSingleFood() {
        if ((food == null) == (personalFood == null)) {
            throw new IllegalStateException("MealItem must reference exactly one of food or personalFood");
        }
    }

    public Nutrients nutrients() {
        Nutrients per100g = (food != null ? food.nutrientsPerHundredGrams()
                : personalFood.nutrientsPerHundredGrams());
        return per100g.times(quantityInGrams / 100.0);
    }

}