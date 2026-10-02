package com.gdelboni.vittadiet.repository;

import com.gdelboni.vittadiet.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findById(long id);
}
