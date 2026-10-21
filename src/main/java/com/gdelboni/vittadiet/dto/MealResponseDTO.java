package com.gdelboni.vittadiet.dto;

import com.gdelboni.vittadiet.entities.Nutrients;
import com.gdelboni.vittadiet.enums.MealPeriod;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MealResponseDTO {
    private Long id;
    MealPeriod mealPeriod;
    LocalDateTime mealHour;
    List<MealItemResponseDTO> mealItems;
    Nutrients nutrients;
}
