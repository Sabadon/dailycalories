package com.sabadon.dailycalories.service;

import com.sabadon.dailycalories.dto.meal.MealRequest;
import com.sabadon.dailycalories.dto.meal.MealResponse;
import com.sabadon.dailycalories.dto.mapper.MealMapper;
import com.sabadon.dailycalories.entities.Dish;
import com.sabadon.dailycalories.entities.Meal;
import com.sabadon.dailycalories.entities.User;
import com.sabadon.dailycalories.repository.DishRepository;
import com.sabadon.dailycalories.repository.MealRepository;
import com.sabadon.dailycalories.util.MealUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MealService {

    private UserService userService;
    private MealRepository mealRepository;
    private DishRepository dishRepository;
    private MealMapper mealMapper;

    @Transactional
    public MealResponse createMeal(Long userId, MealRequest request) {
        final User user = userService.tryFindUser(userId);

        final Meal meal = new Meal();
        meal.setUser(user);
        meal.setMealTime(request.mealTime());

        addAllDishesToMeal(request.dishes(), meal);

        meal.setTotalCalories(calculateTotalMealCalories(meal));

        final Meal savedMeal = mealRepository.save(meal);
        return mealMapper.toMealResponse(savedMeal);
    }

    public List<MealResponse> getAllUserMeals(Long userId) {
        final User user = userService.tryFindUser(userId);

        final List<Meal> meals = mealRepository.findByUserOrderByMealTimeDesc(user);

        return meals.stream()
                .map(mealMapper::toMealResponse)
                .toList();
    }

    public List<MealResponse> getAllUserMealsFromDate(Long userId, LocalDate date) {
        final User user = userService.tryFindUser(userId);

        final LocalDateTime startDate = date.atStartOfDay();
        final LocalDateTime endDate = date.atTime(LocalTime.MAX);

        final List<Meal> meals = mealRepository.findByUserAndMealTimeBetween(
                user,
                startDate,
                endDate,
                Sort.by(Sort.Direction.DESC, "mealTime")
        );

        return meals.stream()
                .map(mealMapper::toMealResponse)
                .toList();
    }

    public MealResponse getMeal(Long mealId) {
        final Meal meal = tryFindMeal(mealId);

        return mealMapper.toMealResponse(meal);
    }

    public Page<MealRepository.DailyCaloriesTotal> getDailyCaloriesHistory(Long userId, Pageable pageable) {
        final User user = userService.tryFindUser(userId);
        return mealRepository.getDailyCaloriesHistory(user, pageable);
    }

    @Transactional
    public void deleteMeal(Long mealId) {
        final Meal meal = tryFindMeal(mealId);
        mealRepository.delete(meal);
    }

    @Transactional
    public MealResponse updateMeal(Long mealId, MealRequest request) {
        final Meal meal = tryFindMeal(mealId);
        meal.setMealTime(request.mealTime());
        meal.getMealDishes().clear();

        addAllDishesToMeal(request.dishes(), meal);
        meal.setTotalCalories(calculateTotalMealCalories(meal));

        mealRepository.save(meal);

        return mealMapper.toMealResponse(meal);
    }

    private void addAllDishesToMeal(List<MealRequest.MealDishRequest> dishes, Meal meal) {
        dishes.forEach(mealDishRequest -> {
            final Dish dish = dishRepository.findById(mealDishRequest.dishId())
                    .orElseThrow(() -> new EntityNotFoundException("Dish with id " + mealDishRequest.dishId() + " not found"));

            meal.addDish(dish, mealDishRequest.portionSize());
        });
    }

    private BigDecimal calculateTotalMealCalories(Meal meal) {
        return meal.getMealDishes().stream()
                .map(MealUtil::calculateMealDishCalories)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Meal tryFindMeal(Long mealId) {
        return mealRepository.findWithDetailsById(mealId)
                .orElseThrow(() -> new EntityNotFoundException("Meal with id " + mealId + " not found"));
    }
}
