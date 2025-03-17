package com.sabadon.dailycalories.dto;

import com.sabadon.dailycalories.enums.Gender;
import com.sabadon.dailycalories.enums.Goal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UserDto(Long id,
                      @NotBlank(message = "Имя не может быть пустым")
                      String name,
                      @NotBlank(message = "Почта не может быть пустой")
                      @Email(message = "Неккоректный формат email")
                      String email,
                      @Positive(message = "Возраст должен быть положительным")
                      Integer age,
                      @Positive(message = "Вес должен быть положительным")
                      BigDecimal weight,
                      @Positive(message = "Рост должен быть положительным")
                      BigDecimal height,
                      Gender gender,
                      Goal goal) {
}
