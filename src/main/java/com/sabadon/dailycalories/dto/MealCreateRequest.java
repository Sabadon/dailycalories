package com.sabadon.dailycalories.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record MealCreateRequest(
        LocalDateTime mealTime,
        @NotEmpty(message = "Прием пищи не может быть без блюд")
        List<MealDishRequest> dishes
) {
    public record MealDishRequest (
            @NotNull(message = "Идентификатор блюда не может быть пустым")
            Long dishId,
            @NotNull(message = "Размер порции не может быть пустым")
            @Positive(message = "Размер порции должен быть положительным")
            Long portionSize
    ) {
    }
}
