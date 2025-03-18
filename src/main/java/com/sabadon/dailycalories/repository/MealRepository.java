package com.sabadon.dailycalories.repository;

import com.sabadon.dailycalories.entities.Meal;
import com.sabadon.dailycalories.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {

    @EntityGraph(
            value = "Meal.withDishesAndDetails",
            type = EntityGraphType.FETCH
    )
    Optional<Meal> findWithDetailsById(Long id);

    @EntityGraph(
            value = "Meal.withDishesAndDetails",
            type = EntityGraphType.FETCH
    )
    List<Meal> findByUserAndMealTimeBetween(
            User user,
            LocalDateTime start,
            LocalDateTime end,
            Sort sort
    );

    @EntityGraph(
            value = "Meal.withDishesAndDetails",
            type = EntityGraphType.FETCH
    )
    List<Meal> findByUserOrderByMealTimeDesc(User user);


    @Query("""
        SELECT
            CAST(m.mealTime AS LocalDate) as date,
            COUNT(m) as mealsCount,
            SUM(md.dish.caloriesPerPortion / 100 * md.portionSize) as totalCalories
        FROM Meal m
        JOIN m.mealDishes md
        WHERE m.user = :user
        GROUP BY CAST(m.mealTime AS LocalDate)
        ORDER BY date DESC
        """)
    Page<DailyCaloriesTotal> getDailyCaloriesHistory(
            @Param("user") User user,
            Pageable pageable
    );

    interface DailyCaloriesTotal {
        LocalDate getDate();
        int getMealsCount();
        BigDecimal getTotalCalories();
    }



}
