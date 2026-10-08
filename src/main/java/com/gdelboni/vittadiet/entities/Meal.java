package com.gdelboni.vittadiet.entities;

import java.time.LocalDateTime;
import java.util.List;
import com.gdelboni.vittadiet.enums.MealPeriod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NullMarked;

@Table(name = "meal")
@Entity(name = "meal")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NullMarked
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MealPeriod mealPeriod;
    private List<MealItem> mealItems;
    private LocalDateTime mealHour;

    public Meal(MealPeriod mealPeriod, List<MealItem> mealItems, LocalDateTime mealHour) {
        this.mealPeriod = mealPeriod;
        this.mealItems = mealItems;
        this.mealHour = mealHour;
    }

    public void addFood(MealItem mealItem) {
        mealItems.add(mealItem);
    }

    public void removeMealItem(MealItem mealItem) {
        mealItems.remove(mealItem);
    }

    public void changePeriod(MealPeriod mealPeriod) {
        this.mealPeriod = mealPeriod;
    }

    public void changeHour(LocalDateTime mealHour) {
        this.mealHour = mealHour;
    }
}
