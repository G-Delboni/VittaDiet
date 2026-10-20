package com.gdelboni.vittadiet.repository;

import com.gdelboni.vittadiet.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findById(long id);

    @Query("""
        select distinct m from meal m
        left join fetch m.mealItems i
        left join fetch i.food
        left join fetch i.personalFood
        where m.id = :id and m.user.id = :userId
        """)
    Optional<Meal> findDetailedByIdAndUserId(@Param("id") Long id, @Param("userId") String userId);
}
