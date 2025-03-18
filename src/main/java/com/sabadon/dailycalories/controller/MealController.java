package com.sabadon.dailycalories.controller;

import com.sabadon.dailycalories.dto.MealCreateRequest;
import com.sabadon.dailycalories.dto.MealResponse;
import com.sabadon.dailycalories.service.MealService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user/{userId}/meal")
public class MealController {

    private MealService mealService;

    @PostMapping
    public ResponseEntity<MealResponse> createMeal(@PathVariable Long userId,
                                                   @RequestBody @Validated MealCreateRequest request,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        final MealResponse meal = mealService.createMeal(userId, request);
        final URI location = uriComponentsBuilder.path("/api/user/{userId}/meal/{mealId}")
                .buildAndExpand(userId, meal.id()).toUri();
        return ResponseEntity
                .created(location)
                .body(meal);
    }

}
