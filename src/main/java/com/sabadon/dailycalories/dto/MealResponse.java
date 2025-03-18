package com.sabadon.dailycalories.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record MealResponse(
        Long id,
        LocalDateTime mealTime,
        List<MealDishResponse> dishes,
        BigDecimal totalCalories
) {
    public record MealDishResponse(
            String name,
            BigDecimal portionSize,
            BigDecimal calories
    ) {
    }
}
