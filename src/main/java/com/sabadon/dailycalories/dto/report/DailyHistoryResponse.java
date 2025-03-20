package com.sabadon.dailycalories.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Сущность истории питания")
public record DailyHistoryResponse(
        @Schema(description = "Содержимое отчета")
        List<DailyTotal> content,
        @Schema(description = "Информация о странице")
        PageInfo page
) {
    public record DailyTotal(
            @Schema(description = "Дата")
            LocalDate date,
            @Schema(description = "Всего приемов пищи")
            int mealsCount,
            @Schema(description = "Всего потреблено калорий")
            BigDecimal totalCalories
    ) {
    }
    @Schema(description = "Сущность информации о странице")
    public record PageInfo(
            @Schema(description = "Текущая страница")
            int currentPage,
            @Schema(description = "Всего страниц")
            int totalPages,
            @Schema(description = "Всего элементов")
            long totalItems
    ) {
    }
}
