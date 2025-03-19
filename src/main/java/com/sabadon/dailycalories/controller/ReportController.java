package com.sabadon.dailycalories.controller;

import com.sabadon.dailycalories.dto.report.DailyCalorieStatusResponse;
import com.sabadon.dailycalories.dto.report.DailyHistoryResponse;
import com.sabadon.dailycalories.dto.report.DailyReportResponse;
import com.sabadon.dailycalories.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user/{userId}/reports")
@Tag(name = "Отчеты", description = "Получение отчетов")
public class ReportController {

    private ReportService service;

    @GetMapping("/daily")
    @Operation(
            summary = "Получение отчета за день",
            description = "Позволяет получить отчет за день с суммой всех калорий и приемов пищи"
    )
    public ResponseEntity<DailyReportResponse> getDailyReport(@PathVariable
                                                              @Parameter(description = "Идентификатор пользователя", required = true)
                                                              Long userId,
                                                              @RequestParam
                                                              @Parameter(description = "Дата для отчета", example = "2025-03-17T12:00:55.181Z", required = true)
                                                              LocalDate date) {
        return ResponseEntity.ok(
                service.getDailyReport(userId, date)
        );
    }

    @GetMapping("/daily-status")
    @Operation(
            summary = "Получение отчета о дневной норме",
            description = "Позволяет получить отчет уложился ли пользователь в свою дневную норму калорий"
    )
    public ResponseEntity<DailyCalorieStatusResponse> getDailyStatusReport(@PathVariable
                                                                           @Parameter(description = "Идентификатор пользователя", required = true)
                                                                           Long userId,
                                                                           @RequestParam
                                                                           @Parameter(description = "Дата для отчета", example = "2025-03-17T12:00:55.181Z", required = true)
                                                                           LocalDate date) {
        return ResponseEntity.ok(
                service.getDailyCalorieStatus(userId, date)
        );
    }

    @GetMapping("/history")
    @Operation(
            summary = "Получение истории питания",
            description = "Позволяет получить отчет с историей питания"
    )
    public ResponseEntity<DailyHistoryResponse> getDailyHistory(@PathVariable
                                                                @Parameter(description = "Идентификатор пользователя", required = true)
                                                                Long userId,
                                                                @RequestParam(defaultValue = "0")
                                                                @Parameter(description = "Номер страницы")
                                                                int page,
                                                                @RequestParam(defaultValue = "30")
                                                                @Parameter(description = "Размер страницы")
                                                                int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return ResponseEntity.ok(
                service.getDailyHistory(userId, pageable)
        );
    }

}
