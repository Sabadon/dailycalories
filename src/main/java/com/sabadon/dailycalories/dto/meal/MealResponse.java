package com.sabadon.dailycalories.dto.meal;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Сущность информации о приеме пищи")
public record MealResponse(
        @Schema(description = "Идентификатор приема пищи", example = "123")
        Long id,
        @Schema(description = "Время приема пищи", example = "2025-01-01T12:00:00.999Z")
        LocalDateTime mealTime,
        @Schema(description = "Список блюд")
        List<MealDishResponse> dishes,
        @Schema(description = "Всего калорий за прием пищи")
        BigDecimal totalCalories
) {
    @Schema(description = "Сущность информации о блюде во время приема пищи")
    public record MealDishResponse(
            @Schema(description = "Название блюда", example = "Борщ")
            String name,
            @Schema(description = "Размер порции в граммах", example = "200")
            Long portionSize,
            @Schema(description = "Калорий в блюде", example = "115.4")
            BigDecimal calories
    ) {
    }
}
