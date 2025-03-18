package com.sabadon.dailycalories.repository;

import com.sabadon.dailycalories.entities.Meal;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {

    @Override
    @EntityGraph(attributePaths = {"mealDishes", "mealDishes.dish", "user"})
    Optional<Meal> findById(@Param("id") Long id);

}
