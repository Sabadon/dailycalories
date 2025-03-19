package com.sabadon.dailycalories.dto.meal;

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
            Long portionSize,
            BigDecimal calories
    ) {
    }
}
