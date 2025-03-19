package com.sabadon.dailycalories.service;

import com.sabadon.dailycalories.dto.mapper.DailyHistoryMapper;
import com.sabadon.dailycalories.dto.mapper.MealMapper;
import com.sabadon.dailycalories.dto.meal.MealResponse;
import com.sabadon.dailycalories.dto.report.DailyCalorieStatusResponse;
import com.sabadon.dailycalories.dto.report.DailyHistoryResponse;
import com.sabadon.dailycalories.dto.report.DailyReportResponse;
import com.sabadon.dailycalories.entities.User;
import com.sabadon.dailycalories.repository.MealRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {

    private UserService userService;
    private MealService mealService;
    private MealMapper mealItemMapper;
    private DailyHistoryMapper dailyHistoryMapper;

    public DailyReportResponse getDailyReport(Long userId, LocalDate date) {
        final List<MealResponse> meals = mealService.getAllUserMealsFromDate(userId, date);
        final BigDecimal totalCalories = getTotalCalories(meals);
        final List<DailyReportResponse.MealItem> mealItems = meals.stream()
                .map(mealItemMapper::toMealItem)
                .toList();

        return new DailyReportResponse(
                date,
                meals.size(),
                totalCalories,
                mealItems
        );
    }

    public DailyCalorieStatusResponse getDailyCalorieStatus(Long userId, LocalDate date) {
        final User user = userService.tryFindUser(userId);
        final List<MealResponse> meals = mealService.getAllUserMealsFromDate(userId, date);
        final BigDecimal totalCalories = getTotalCalories(meals);
        return new DailyCalorieStatusResponse(
                date,
                user.getDailyCalories(),
                totalCalories,
                totalCalories.compareTo(user.getDailyCalories()) > 0
        );
    }

    private static BigDecimal getTotalCalories(List<MealResponse> meals) {
        return meals.stream()
                .map(MealResponse::totalCalories)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public DailyHistoryResponse getDailyHistory(Long userId, Pageable pageable) {
        final Page<MealRepository.DailyCaloriesTotal> page = mealService.getDailyCaloriesHistory(userId, pageable);
        final List<DailyHistoryResponse.DailyTotal> content = page.getContent().stream()
                .map(dailyHistoryMapper::toDailyTotal)
                .toList();

        return new DailyHistoryResponse(
                content,
                new DailyHistoryResponse.PageInfo(
                        page.getNumber(),
                        page.getTotalPages(),
                        page.getTotalElements()
                )
        );
    }
}
