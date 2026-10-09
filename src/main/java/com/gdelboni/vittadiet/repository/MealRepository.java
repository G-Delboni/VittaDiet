package com.gdelboni.vittadiet.repository;

import com.gdelboni.vittadiet.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findById(long id);
}
