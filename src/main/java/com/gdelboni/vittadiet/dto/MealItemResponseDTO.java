package com.gdelboni.vittadiet.dto;

import com.gdelboni.vittadiet.entities.Nutrients;
import lombok.Getter;

@Getter
public class MealItemResponseDTO {
    private Long id;
    private String foodName;
    private Double quantityInGrams;
    private Nutrients nutrients;
}
