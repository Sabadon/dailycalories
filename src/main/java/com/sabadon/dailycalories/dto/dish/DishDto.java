package com.sabadon.dailycalories.dto.dish;

import com.sabadon.dailycalories.dto.marker.OnCreate;
import com.sabadon.dailycalories.dto.marker.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Сущность блюда")
public record DishDto(
        @Schema(description = "Уникальный идентификатор блюда", example = "123", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
        @Schema(description = "Название блюда", example = "Борщ")
        @NotBlank(groups = OnCreate.class, message = "Название блюда не может быть пустым")
        String name,
        @Schema(description = "Количество калорий", example = "57.7")
        @NotNull(groups = OnCreate.class, message = "Количество калорий за порцию не может быть пустым")
        @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Количество калорий должно быть положительным")
        BigDecimal caloriesPerPortion,
        @Schema(description = "Количество белков", example = "3.8")
        @NotNull(groups = OnCreate.class, message = "Количество белков за порцию не может быть пустым")
        @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Количество белков должно быть положительным")
        BigDecimal proteins,
        @Schema(description = "Количество жиров", example = "2.9")
        @NotNull(groups = OnCreate.class, message = "Количество жиров за порцию не может быть пустым")
        @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Количество жиров должно быть положительным")
        BigDecimal fats,
        @Schema(description = "Количество углеводов", example = "4.3")
        @NotNull(groups = OnCreate.class, message = "Количество углеводов за порцию не может быть пустым")
        @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Количество углеводов должно быть положительным")
        BigDecimal carbs
) {
}
