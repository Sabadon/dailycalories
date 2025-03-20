package com.sabadon.dailycalories.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Сущность отчета о приемах пищи за день")
public record DailyReportResponse(
        @Schema(description = "Дата отчета")
        LocalDate date,
        @Schema(description = "Всего приемов пищи за день")
        int totalMeals,
        @Schema(description = "Всего потреблено калорий")
        BigDecimal totalCalories,
        @Schema(description = "Список приемов пищи")
        List<MealItem> meals
) {
    @Schema(description = "Сущность элемента приема пищи в отчете")
    public record MealItem(
            @Schema(description = "Время приема пищи", example = "2025-01-01T12:00:00.999Z")
            LocalDateTime mealTime,
            @Schema(description = "Список блюд")
            List<MealDishItem> dishes,
            @Schema(description = "Всего калорий за прием пищи")
            BigDecimal totalCalories
    ) {
    }
    @Schema(description = "Сущность элемента блюда в отчете")
    public record MealDishItem(
            @Schema(description = "Название блюда", example = "Борщ")
            String name,
            @Schema(description = "Размер порции в граммах", example = "200")
            Long portionSize,
            @Schema(description = "Калорий в блюде", example = "115.4")
            BigDecimal calories
    ) {
    }
}
