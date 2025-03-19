package com.sabadon.dailycalories.service;

import com.sabadon.dailycalories.dto.mapper.DailyHistoryMapper;
import com.sabadon.dailycalories.dto.mapper.MealMapper;
import com.sabadon.dailycalories.dto.meal.MealResponse;
import com.sabadon.dailycalories.dto.meal.MealResponse.MealDishResponse;
import com.sabadon.dailycalories.dto.report.DailyCalorieStatusResponse;
import com.sabadon.dailycalories.dto.report.DailyHistoryResponse;
import com.sabadon.dailycalories.dto.report.DailyHistoryResponse.DailyTotal;
import com.sabadon.dailycalories.dto.report.DailyReportResponse;
import com.sabadon.dailycalories.dto.report.DailyReportResponse.MealDishItem;
import com.sabadon.dailycalories.dto.report.DailyReportResponse.MealItem;
import com.sabadon.dailycalories.entities.User;
import com.sabadon.dailycalories.enums.Gender;
import com.sabadon.dailycalories.enums.Goal;
import com.sabadon.dailycalories.repository.MealRepository.DailyCaloriesTotal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private MealService mealService;
    @Mock
    private MealMapper mealMapper;
    @Mock
    private DailyHistoryMapper dailyHistoryMapper;
    @InjectMocks
    private ReportService reportService;

    /*getDailyReport, getDailyReport
     * корректный вызов со списком приемов пищи
     * вызов в день когда нет приемов пищи
     * */

    @Test
    void testDailyReport() {
        final Long userId = 1L;
        final MealResponse meal1 = createMealResponse(1L,
                List.of(
                        new MealDishResponse("Borsch", 200L, BigDecimal.valueOf(200)),
                        new MealDishResponse("Buckwheat", 50L, BigDecimal.valueOf(100))
                )
        );
        final MealResponse meal2 = createMealResponse(2L,
                List.of(
                        new MealDishResponse("Borsch", 50L, BigDecimal.valueOf(50))
                )
        );
        final MealItem expectedMealItem1 = createMealItemFromMealResponse(meal1);
        final MealItem expectedMealItem2 = createMealItemFromMealResponse(meal2);

        when(mealService.getAllUserMealsFromDate(eq(userId), any(LocalDate.class)))
                .thenReturn(List.of(meal2, meal1));
        when(mealMapper.toMealItem(meal1)).thenReturn(expectedMealItem1);
        when(mealMapper.toMealItem(meal2)).thenReturn(expectedMealItem2);

        final DailyReportResponse dailyReport = reportService.getDailyReport(userId, LocalDate.now());

        assertEquals(LocalDate.now(), dailyReport.date());
        assertEquals(2, dailyReport.meals().size());
        assertEquals(BigDecimal.valueOf(350), dailyReport.totalCalories());
    }

    @Test
    void testDailyReportNoMealsAtDay() {
        final Long userId = 1L;

        when(mealService.getAllUserMealsFromDate(eq(userId), any(LocalDate.class)))
                .thenReturn(List.of());

        final DailyReportResponse dailyReport = reportService.getDailyReport(userId, LocalDate.now());

        assertEquals(LocalDate.now(), dailyReport.date());
        assertEquals(0, dailyReport.meals().size());
    }

    @Test
    void testDailyCalorieStatus() {
        final User user = new User(
                1L,
                "Олег Петрович",
                "coololeg@test.com",
                35,
                BigDecimal.valueOf(102),
                BigDecimal.valueOf(175),
                Gender.MALE,
                Goal.LOSE,
                BigDecimal.valueOf(2263.66)
        );
        final MealResponse meal1 = createMealResponse(1L,
                List.of(
                        new MealDishResponse("Borsch", 200L, BigDecimal.valueOf(200)),
                        new MealDishResponse("Buckwheat", 50L, BigDecimal.valueOf(100))
                )
        );
        final MealResponse meal2 = createMealResponse(2L,
                List.of(
                        new MealDishResponse("Borsch", 50L, BigDecimal.valueOf(50))
                )
        );

        when(userService.tryFindUser(user.getId())).thenReturn(user);
        when(mealService.getAllUserMealsFromDate(eq(user.getId()), any(LocalDate.class)))
                .thenReturn(List.of(meal2, meal1));

        final DailyCalorieStatusResponse resultStatus = reportService.getDailyCalorieStatus(user.getId(), LocalDate.now());

        assertEquals(LocalDate.now(), resultStatus.date());
        assertEquals(user.getDailyCalories(), resultStatus.dailyCalories());
        assertEquals(BigDecimal.valueOf(350), resultStatus.consumedCalories());
        assertFalse(resultStatus.isExceeded());

    }

    @Test
    void testDailyHistory() {
        final Long userId = 1L;
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("date").descending());
        final LocalDate date = LocalDate.now();

        final DailyCaloriesTotal mockTotal = mock(DailyCaloriesTotal.class);
        when(mockTotal.getDate()).thenReturn(date);
        when(mockTotal.getMealsCount()).thenReturn(3);
        when(mockTotal.getTotalCalories()).thenReturn(new BigDecimal(1000));
        final Page<DailyCaloriesTotal> mockPage = new PageImpl<>(
                Collections.singletonList(mockTotal),
                pageable,
                1
        );
        final DailyTotal expectedMappedTotal = new DailyTotal(
                LocalDate.now(),
                3,
                BigDecimal.valueOf(1000)
        );

        when(mealService.getDailyCaloriesHistory(userId, pageable)).thenReturn(mockPage);
        when(dailyHistoryMapper.toDailyTotal(mockTotal)).thenReturn(expectedMappedTotal);

        final DailyHistoryResponse result = reportService.getDailyHistory(userId, pageable);

        assertEquals(1, result.content().size());
        final DailyTotal resultTotal = result.content().getFirst();

        assertEquals(mockTotal.getDate(), resultTotal.date());
        assertEquals(mockTotal.getMealsCount(), resultTotal.mealsCount());
        assertEquals(mockTotal.getTotalCalories(), resultTotal.totalCalories());

        assertEquals(mockPage.getNumber(), result.page().currentPage());
        assertEquals(mockPage.getTotalPages(), result.page().totalPages());
        assertEquals(mockPage.getTotalElements(), result.page().totalItems());
    }

    private MealResponse createMealResponse(Long id, List<MealDishResponse> dishes) {
        final BigDecimal totalCalories = dishes.stream()
                .map(MealDishResponse::calories)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new MealResponse(
                id,
                LocalDateTime.now(),
                dishes,
                totalCalories
        );
    }

    private MealItem createMealItemFromMealResponse(MealResponse meal) {
        return new MealItem(
                meal.mealTime(),
                meal.dishes().stream()
                        .map(md -> new MealDishItem(md.name(), md.portionSize(), md.calories()))
                        .toList(),
                meal.totalCalories()
        );
    }

}
