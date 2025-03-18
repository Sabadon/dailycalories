package com.sabadon.dailycalories.dto.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DailyHistoryResponse(
    List<DailyTotal> content,
    PageInfo page
) {
    public record DailyTotal (
        LocalDate date,
        int mealsCount,
        BigDecimal totalCalories
    ) {
    }
    public record PageInfo(
        int currentPage,
        int totalPages,
        long totalItems
    ) {
    }
}
