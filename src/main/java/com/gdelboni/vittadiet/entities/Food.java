package com.gdelboni.vittadiet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NullMarked;

@Table(name = "food")
@Entity(name = "food")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NullMarked
public class Food {
    @Id
    private Long id;
    @Column(unique = true)
    private Integer tacoCode;
    private String name;
    private Double caloriePerHundredGrams;
    private Double fatPerHundredGrams;
    private Double carbohydratePerHundredGrams;
    private Double proteinPerHundredGrams;


}
