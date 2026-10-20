package com.gdelboni.vittadiet.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.gdelboni.vittadiet.enums.MealPeriod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NullMarked;

@Table(name = "meal")
@Entity(name = "meal")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NullMarked
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MealPeriod mealPeriod;
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealItem> mealItems = new ArrayList<>();
    private LocalDateTime mealHour;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public Nutrients totalNutrients() {
        return mealItems.stream()
                .map(MealItem::nutrients)
                .reduce(Nutrients.ZERO, Nutrients::plus);
    }

    public void addFood(MealItem mealItem) {
        mealItems.add(mealItem);
    }

    public void removeMealItem(MealItem mealItem) {
        mealItems.remove(mealItem);
    }

    public void changePeriod(MealPeriod mealPeriod) {
        this.mealPeriod = mealPeriod;
    }

    public void changeHour(LocalDateTime mealHour) {
        this.mealHour = mealHour;
    }
}
