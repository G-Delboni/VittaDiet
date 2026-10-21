package com.gdelboni.vittadiet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealItemRequestDTO {
    private Long foodId;              // alimento da tabela food (CSV)
    private Long personalFoodId;      // OU alimento personalizado do próprio usuário

    @NotNull
    @Positive
    @Max(10000)
    private Double quantityInGrams;

    @JsonIgnore
    @AssertTrue(message = "Inform exactly one of foodId or personalFoodId")
    public boolean isExactlyOneFood() {
        return (foodId == null) != (personalFoodId == null);
    }
}