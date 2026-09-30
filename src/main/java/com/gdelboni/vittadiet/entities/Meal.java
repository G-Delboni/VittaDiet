package com.gdelboni.vittadiet.entities;

import java.time.LocalDateTime;
import java.util.List;
import com.gdelboni.vittadiet.enums.MealPeriod;
import lombok.Getter;

@Getter
public class Meal {
    private MealPeriod mealPeriod;
    private final List<Food> mealComponents;
    private LocalDateTime mealHour;

    public Meal(MealPeriod mealPeriod, List<Food> mealComponents, LocalDateTime mealHour) {
        this.mealPeriod = mealPeriod;
        this.mealComponents = mealComponents;
        this.mealHour = mealHour;
    }

    public void addFood(Food food) {
        mealComponents.add(food);
    }

    public void removeFood(Food food) {
        mealComponents.remove(food);
    }

    public void changePeriod(MealPeriod mealPeriod) {
        this.mealPeriod = mealPeriod;
    }

    public void changeHour(LocalDateTime mealHour) {
        this.mealHour = mealHour;
    }
}
