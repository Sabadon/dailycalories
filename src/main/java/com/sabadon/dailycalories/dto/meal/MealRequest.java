package com.sabadon.dailycalories.dto.meal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Сущность приема пищи")
public record MealRequest(
        @Schema(description = "Время приема пищи", example = "2025-01-01T12:00:00.999Z")
        @NotNull(message = "Время приема пищи не может быть пустым")
        LocalDateTime mealTime,
        @Schema(description = "Список блюд")
        @NotEmpty(message = "Прием пищи не может быть без блюд")
        @Valid
        List<MealDishRequest> dishes
) {
    @Schema(description = "Сущность блюда во время приема пищи")
    public record MealDishRequest(
            @Schema(description = "Идентификатор блюда", example = "123")
            @NotNull(message = "Идентификатор блюда не может быть пустым")
            Long dishId,
            @Schema(description = "Размер порции в граммах", example = "200")
            @NotNull(message = "Размер порции не может быть пустым")
            @Positive(message = "Размер порции должен быть положительным")
            Long portionSize
    ) {
    }
}
