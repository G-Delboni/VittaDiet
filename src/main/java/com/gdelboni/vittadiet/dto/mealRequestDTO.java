package com.gdelboni.vittadiet.dto;

import com.gdelboni.vittadiet.entities.Food;
import com.gdelboni.vittadiet.enums.MealPeriod;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class mealRequestDTO {
    private MealPeriod mealPeriod;
    private List<Food> mealComponents;
    private LocalDateTime mealHour;
}
