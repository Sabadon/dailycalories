package com.sabadon.dailycalories.dto.user;

import com.sabadon.dailycalories.dto.marker.OnCreate;
import com.sabadon.dailycalories.dto.marker.OnUpdate;
import com.sabadon.dailycalories.enums.Gender;
import com.sabadon.dailycalories.enums.Goal;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UserDto(Long id,
                      @NotBlank(groups = OnCreate.class, message = "Имя не может быть пустым")
                      String name,
                      @NotBlank(groups = OnCreate.class, message = "Почта не может быть пустой")
                      @Email(groups = {OnCreate.class, OnUpdate.class}, message = "Неккоректный формат email")
                      String email,
                      @NotNull(groups = OnCreate.class, message = "Возраст не должен быть пустым")
                      @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Возраст должен быть положительным")
                      @Min(groups = {OnCreate.class, OnUpdate.class}, value = 10, message = "Возраст должен быть больше 10")
                      @Max(groups = {OnCreate.class, OnUpdate.class}, value = 120, message = "Возраст должен быть меньше 120")
                      Integer age,
                      @NotNull(groups = OnCreate.class, message = "Вес не должен быть пустым")
                      @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Вес должен быть положительным")
                      BigDecimal weight,
                      @NotNull(groups = OnCreate.class, message = "Рост не должен быть пустым")
                      @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "Рост должен быть положительным")
                      BigDecimal height,
                      @NotNull(groups = OnCreate.class, message = "Пол не должен быть пустым")
                      Gender gender,
                      @NotNull(groups = OnCreate.class, message = "Цель не должна быть пустой")
                      Goal goal,
                      BigDecimal dailyCalories
) {
}
