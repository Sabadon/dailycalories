package com.sabadon.dailycalories.controller;

import com.sabadon.dailycalories.dto.meal.MealRequest;
import com.sabadon.dailycalories.dto.meal.MealResponse;
import com.sabadon.dailycalories.service.MealService;
import com.sabadon.dailycalories.service.UserService;
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
public class MealController {

    private MealService mealService;
    private UserService userService;

    @PostMapping
    public ResponseEntity<MealResponse> createMeal(@PathVariable Long userId,
                                                   @RequestBody @Validated MealRequest request,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        final MealResponse meal = mealService.createMeal(userId, request);
        final URI location = uriComponentsBuilder.path("/api/user/{userId}/meal/{mealId}")
                .buildAndExpand(userId, meal.id()).toUri();
        return ResponseEntity
                .created(location)
                .body(meal);
    }

    @GetMapping
    public ResponseEntity<List<MealResponse>> getAllMeals(@PathVariable Long userId,
                                                          @RequestParam(required = false) LocalDate date) {
        final List<MealResponse> meals = date != null
                ? mealService.getAllUserMealsFromDate(userId, date)
                : mealService.getAllUserMeals(userId);

        return ResponseEntity.ok(meals);
    }

    @GetMapping("/{mealId}")
    public ResponseEntity<MealResponse> getMeal(@PathVariable Long userId,
                                                @PathVariable Long mealId) {
        userService.tryFindUser(userId);

        return ResponseEntity.ok(
                mealService.getMeal(mealId)
        );
    }

    @PutMapping("/{mealId}")
    public ResponseEntity<MealResponse> updateMeal(@PathVariable Long userId,
                                                   @PathVariable Long mealId,
                                                   @RequestBody @Validated MealRequest request) {
        userService.tryFindUser(userId);

        return ResponseEntity.ok(
                mealService.updateMeal(mealId, request)
        );
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long userId,
                                           @PathVariable Long mealId) {
        userService.tryFindUser(userId);
        mealService.deleteMeal(mealId);

        return ResponseEntity.noContent().build();
    }

}
