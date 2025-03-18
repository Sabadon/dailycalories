package com.sabadon.dailycalories.dto.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DailyReportResponse(
        LocalDate date,
        int totalMeals,
        BigDecimal totalCalories,
        List<MealItem> meals
) {
    public record MealItem(
            LocalDateTime mealTime,
            List<MealDishItem> dishes,
            BigDecimal totalCalories
    ) {
    }
    public record MealDishItem(
            String name,
            BigDecimal portionSize,
            BigDecimal calories
    ) {
    }
}
