package com.sabadon.dailycalories.dto.dish;

import com.sabadon.dailycalories.dto.marker.OnCreate;
import com.sabadon.dailycalories.dto.marker.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DishDto(
        Long id,
        @NotBlank(groups = OnCreate.class, message = "Название блюда не может быть пустым")
        String name,
        @NotNull(groups = OnCreate.class, message = "Количество калорий за порцию не может быть пустым")
        @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Количество калорий должно быть положительным")
        BigDecimal caloriesPerPortion,
        @NotNull(groups = OnCreate.class, message = "Количество белков за порцию не может быть пустым")
        @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Количество белков должно быть положительным")
        BigDecimal proteins,
        @NotNull(groups = OnCreate.class, message = "Количество жиров за порцию не может быть пустым")
        @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Количество жиров должно быть положительным")
        BigDecimal fats,
        @NotNull(groups = OnCreate.class, message = "Количество углеводов за порцию не может быть пустым")
        @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Количество углеводов должно быть положительным")
        BigDecimal carbs
) {
}
