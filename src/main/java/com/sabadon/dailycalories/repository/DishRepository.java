package com.sabadon.dailycalories.repository;

import com.sabadon.dailycalories.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
