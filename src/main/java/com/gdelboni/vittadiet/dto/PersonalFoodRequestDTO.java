package com.gdelboni.vittadiet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalFoodRequestDTO {
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull @PositiveOrZero @Max(900)          // gordura pura tem ~900 kcal/100 g
    private Double caloriePerHundredGrams;

    @PositiveOrZero
    @Max(100) private Double proteinPerHundredGrams;
    @PositiveOrZero @Max(100) private Double carbohydratePerHundredGrams;
    @PositiveOrZero @Max(100) private Double fatPerHundredGrams;

    // Em 100 g de alimento não cabem mais de 100 g de macros somados
    @JsonIgnore
    @AssertTrue(message = "Protein + carbohydrate + fat cannot exceed 100 g per 100 g")
    public boolean isMacrosWithinHundredGrams() {
        double sum = (proteinPerHundredGrams == null ? 0 : proteinPerHundredGrams)
                + (carbohydratePerHundredGrams == null ? 0 : carbohydratePerHundredGrams)
                + (fatPerHundredGrams == null ? 0 : fatPerHundredGrams);
        return sum <= 100;
    }
}