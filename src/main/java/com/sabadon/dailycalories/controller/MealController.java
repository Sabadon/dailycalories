package com.sabadon.dailycalories.controller;

import com.sabadon.dailycalories.dto.meal.MealRequest;
import com.sabadon.dailycalories.dto.meal.MealResponse;
import com.sabadon.dailycalories.service.MealService;
import com.sabadon.dailycalories.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user/{userId}/meal")
@Tag(name = "Приемы пищи", description = "Взаимодействие с приемами пищи")
public class MealController {

    private MealService mealService;
    private UserService userService;

    @PostMapping
    @Operation(
            summary = "Добавление приема пищи",
            description = "Позволяет добавить прием пищи для конкретного пользователя"
    )
    public ResponseEntity<MealResponse> createMeal(@PathVariable
                                                   @Parameter(description = "Идентификатор пользователя", required = true)
                                                   Long userId,
                                                   @RequestBody
                                                   @Validated
                                                   MealRequest request,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        final MealResponse meal = mealService.createMeal(userId, request);
        final URI location = uriComponentsBuilder.path("/api/user/{userId}/meal/{mealId}")
                .buildAndExpand(userId, meal.id()).toUri();
        return ResponseEntity
                .created(location)
                .body(meal);
    }

    @GetMapping
    @Operation(
            summary = "Получение всех приемов пищи",
            description = "Позволяет получить все приемы пищи пользователя за все время или за указаную дату"
    )
    public ResponseEntity<List<MealResponse>> getAllMeals(@PathVariable
                                                          @Parameter(description = "Идентификатор пользователя", required = true)
                                                          Long userId,
                                                          @RequestParam(required = false)
                                                          @Parameter(description = "Дата приемов пищи", example = "2025-03-17T12:00:55.181Z")
                                                          LocalDate date) {
        final List<MealResponse> meals = date != null
                ? mealService.getAllUserMealsFromDate(userId, date)
                : mealService.getAllUserMeals(userId);

        return ResponseEntity.ok(meals);
    }

    @GetMapping("/{mealId}")
    @Operation(
            summary = "Получение приема пищи",
            description = "Позволяет получить указанный прием пищи"
    )
    public ResponseEntity<MealResponse> getMeal(@PathVariable
                                                @Parameter(description = "Идентификатор пользователя", required = true)
                                                Long userId,
                                                @PathVariable
                                                @Parameter(description = "Идентификатор приема пищи", required = true)
                                                Long mealId) {
        userService.tryFindUser(userId);

        return ResponseEntity.ok(
                mealService.getMeal(mealId)
        );
    }

    @PutMapping("/{mealId}")
    @Operation(
            summary = "Изменение приема пищи",
            description = "Позволяет полностью изменить информацию о приеме пищи"
    )
    public ResponseEntity<MealResponse> updateMeal(@PathVariable
                                                   @Parameter(description = "Идентификатор пользователя", required = true)
                                                   Long userId,
                                                   @PathVariable
                                                   @Parameter(description = "Идентификатор приема пищи", required = true)
                                                   Long mealId,
                                                   @RequestBody
                                                   @Validated
                                                   MealRequest request) {
        userService.tryFindUser(userId);

        return ResponseEntity.ok(
                mealService.updateMeal(mealId, request)
        );
    }

    @DeleteMapping("/{mealId}")
    @Operation(
            summary = "Удаление приема пищи",
            description = "Позволяет удалить прием пищи"
    )
    public ResponseEntity<Void> deleteMeal(@PathVariable
                                           @Parameter(description = "Идентификатор пользователя", required = true)
                                           Long userId,
                                           @PathVariable
                                           @Parameter(description = "Идентификатор приема пищи", required = true)
                                           Long mealId) {
        userService.tryFindUser(userId);
        mealService.deleteMeal(mealId);

        return ResponseEntity.noContent().build();
    }

}
