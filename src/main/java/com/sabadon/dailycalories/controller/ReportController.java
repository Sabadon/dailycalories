package com.sabadon.dailycalories.controller;

import com.sabadon.dailycalories.dto.report.DailyCalorieStatusResponse;
import com.sabadon.dailycalories.dto.report.DailyHistoryResponse;
import com.sabadon.dailycalories.dto.report.DailyReportResponse;
import com.sabadon.dailycalories.service.ReportService;
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
public class ReportController {

    private ReportService service;

    @GetMapping("/daily")
    public ResponseEntity<DailyReportResponse> getDailyReport(@PathVariable Long userId,
                                                              @RequestParam LocalDate date) {
        return ResponseEntity.ok(
                service.getDailyReport(userId, date)
        );
    }

    @GetMapping("/daily-status")
    public ResponseEntity<DailyCalorieStatusResponse> getDailyStatusReport(@PathVariable Long userId,
                                                                           @RequestParam LocalDate date) {
        return ResponseEntity.ok(
                service.getDailyCalorieStatus(userId, date)
        );
    }

    @GetMapping("/history")
    public ResponseEntity<DailyHistoryResponse> getDailyHistory(@PathVariable Long userId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "30") int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return ResponseEntity.ok(
                service.getDailyHistory(userId, pageable)
        );
    }

}
