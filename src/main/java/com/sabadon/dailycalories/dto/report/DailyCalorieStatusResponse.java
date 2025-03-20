package com.sabadon.dailycalories.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Сущность отчета о дневной норме")
public record DailyCalorieStatusResponse(
        @Schema(description = "Дата отчета")
        LocalDate date,
        @Schema(description = "Необходимое количество калорий в день")
        BigDecimal dailyCalories,
        @Schema(description = "Суммарное потребленое количество калорий")
        BigDecimal consumedCalories,
        @Schema(description = "Превышена ли норма калорий в день")
        boolean isExceeded
) {
}
