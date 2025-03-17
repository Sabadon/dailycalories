package com.sabadon.dailycalories.repository;

import com.sabadon.dailycalories.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}
