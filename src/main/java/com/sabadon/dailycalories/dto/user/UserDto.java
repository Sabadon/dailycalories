package com.sabadon.dailycalories.dto.user;

import com.sabadon.dailycalories.dto.marker.OnCreate;
import com.sabadon.dailycalories.dto.marker.OnUpdate;
import com.sabadon.dailycalories.enums.Gender;
import com.sabadon.dailycalories.enums.Goal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Сущность пользователя")
public record UserDto(
        @Schema(description = "Уникальный идентификатор пользователя", example = "123", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
        @Schema(description = "Имя пользователя", example = "Иванов Иван Иванович")
        @NotBlank(groups = OnCreate.class, message = "Имя не может быть пустым")
        String name,
        @Schema(description = "Почта пользователя", example = "ivanov@example.com")
        @NotBlank(groups = OnCreate.class, message = "Почта не может быть пустой")
        @Email(groups = {OnCreate.class, OnUpdate.class}, message = "Неккоректный формат email")
        String email,
        @Schema(description = "Возраст пользователя", example = "25", minimum = "10", maximum = "120")
        @NotNull(groups = OnCreate.class, message = "Возраст не должен быть пустым")
        @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Возраст должен быть положительным")
        @Min(groups = {OnCreate.class, OnUpdate.class}, value = 10, message = "Возраст должен быть больше 10")
        @Max(groups = {OnCreate.class, OnUpdate.class}, value = 120, message = "Возраст должен быть меньше 120")
        Integer age,
        @Schema(description = "Вес пользователя (кг)", example = "80")
        @NotNull(groups = OnCreate.class, message = "Вес не должен быть пустым")
        @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Вес должен быть положительным")
        BigDecimal weight,
        @Schema(description = "Рост пользователя", example = "180")
        @NotNull(groups = OnCreate.class, message = "Рост не должен быть пустым")
        @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Рост должен быть положительным")
        BigDecimal height,
        @Schema(description = "Пол пользователя", example = "MALE")
        @NotNull(groups = OnCreate.class, message = "Пол не должен быть пустым")
        Gender gender,
        @Schema(description = "Цель пользователя", example = "GAIN")
        @NotNull(groups = OnCreate.class, message = "Цель не должна быть пустой")
        Goal goal,
        @Schema(description = "Суточная норма пользователя", example = "2350.2", accessMode = Schema.AccessMode.READ_ONLY)
        BigDecimal dailyCalories
) {
}
