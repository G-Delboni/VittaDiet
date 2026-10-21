package com.gdelboni.vittadiet.dto;

import com.gdelboni.vittadiet.entities.Food;
import com.gdelboni.vittadiet.enums.MealPeriod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MealRequestDTO {
    @NotNull
    private MealPeriod mealPeriod;
    @NotEmpty @Valid
    private List<MealItemRequestDTO> mealComponents;
    @NotNull
    private LocalDateTime mealHour;
}
