package com.sabadon.dailycalories.dto.report;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyCalorieStatusResponse(
        LocalDate date,
        BigDecimal dailyCalories,
        BigDecimal consumedCalories,
        boolean isExceeded
) {
}
