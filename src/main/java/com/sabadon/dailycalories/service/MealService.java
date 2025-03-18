package com.sabadon.dailycalories.service;

import com.sabadon.dailycalories.dto.MealCreateRequest;
import com.sabadon.dailycalories.dto.MealResponse;
import com.sabadon.dailycalories.dto.mapper.MealMapper;
import com.sabadon.dailycalories.entities.Dish;
import com.sabadon.dailycalories.entities.Meal;
import com.sabadon.dailycalories.entities.User;
import com.sabadon.dailycalories.repository.DishRepository;
import com.sabadon.dailycalories.repository.MealRepository;
import com.sabadon.dailycalories.repository.UserRepository;
import com.sabadon.dailycalories.util.MealUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class MealService {

    private UserRepository userRepository;
    private MealRepository mealRepository;
    private DishRepository dishRepository;
    private MealMapper mealMapper;

    @Transactional
    public MealResponse createMeal(Long userId, MealCreateRequest request) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        final Meal meal = new Meal();
        meal.setUser(user);
        meal.setMealTime(request.mealTime() != null
                ? request.mealTime()
                : LocalDateTime.now()
        );

        request.dishes().forEach(mealDishRequest -> {
            final Dish dish = dishRepository.findById(mealDishRequest.dishId())
                    .orElseThrow(() -> new EntityNotFoundException("Dish with id " + userId + " not found"));

            meal.addDish(dish, mealDishRequest.portionSize());
        });

        meal.setTotalCalories(calculateTotalMealCalories(meal));

        final Meal savedMeal = mealRepository.save(meal);
        return mealMapper.toMealResponse(savedMeal);
    }

    private BigDecimal calculateTotalMealCalories(Meal meal) {
        return meal.getMealDishes().stream()
                .map(MealUtil::calculateMealDishCalories)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
