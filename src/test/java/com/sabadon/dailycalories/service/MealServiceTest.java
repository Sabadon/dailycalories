package com.sabadon.dailycalories.service;

import com.sabadon.dailycalories.dto.mapper.MealMapper;
import com.sabadon.dailycalories.dto.mapper.MealMapperImpl;
import com.sabadon.dailycalories.dto.meal.MealRequest;
import com.sabadon.dailycalories.dto.meal.MealResponse;
import com.sabadon.dailycalories.entities.Dish;
import com.sabadon.dailycalories.entities.Meal;
import com.sabadon.dailycalories.entities.User;
import com.sabadon.dailycalories.enums.Gender;
import com.sabadon.dailycalories.enums.Goal;
import com.sabadon.dailycalories.repository.MealRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private MealRepository mealRepository;
    @Mock
    private DishService dishService;
    @Mock
    private MealMapper mealMapper;
    @InjectMocks
    private MealService mealService;

    private User user;
    private Dish dish1;
    private Dish dish2;

    @BeforeEach
    void setup() {
        user = new User(
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
        dish1 = new Dish(
                1L,
                "Borsch",
                BigDecimal.valueOf(57.7),
                BigDecimal.valueOf(3.8),
                BigDecimal.valueOf(2.9),
                BigDecimal.valueOf(4.3)
        );
        dish2 = new Dish(
                2L,
                "Buckwheat",
                BigDecimal.valueOf(343),
                BigDecimal.valueOf(13),
                BigDecimal.valueOf(3.4),
                BigDecimal.valueOf(72)
        );
    }

    @Test
    void testCreateMeal() {
        final Long userId = user.getId();
        final MealRequest mealRequest = new MealRequest(
                LocalDateTime.now(),
                List.of(
                        new MealRequest.MealDishRequest(1L, 200L),
                        new MealRequest.MealDishRequest(1L, 200L),
                        new MealRequest.MealDishRequest(2L, 200L)
                )
        );

        when(userService.tryFindUser(user.getId())).thenReturn(user);
        when(dishService.tryFindDish(dish1.getId())).thenReturn(dish1);
        when(dishService.tryFindDish(dish2.getId())).thenReturn(dish2);
        when(mealRepository.save(any(Meal.class))).thenAnswer(invocation -> {
            final Meal meal = invocation.getArgument(0);
            meal.setId(1L);
            return meal;
        });
        when(mealMapper.toMealResponse(any(Meal.class)))
                .thenAnswer(invocation -> mapMealToMealResponse(invocation.getArgument(0)));


        final MealResponse result = mealService.createMeal(userId, mealRequest);

        assertNotNull(result.id());
        assertEquals(2, result.dishes().size());

        final MealResponse.MealDishResponse buckwheatMeal = result.dishes().stream()
                .filter(md -> md.name().equals(dish1.getName()))
                .findFirst()
                .orElseThrow();

        assertEquals(400, buckwheatMeal.portionSize());
        assertEquals(calcDishCalsForPortion(dish1, 400L), buckwheatMeal.calories());
    }

    @Test
    void testGetMealExisted() {
        final Meal meal = createTestMeal(1L, user, "2025-03-17T12:32:55");

        when(mealRepository.findWithDetailsById(meal.getId())).thenReturn(Optional.of(meal));
        when(mealMapper.toMealResponse(any(Meal.class)))
                .thenAnswer(invocation -> mapMealToMealResponse(invocation.getArgument(0)));

        final MealResponse foundMeal = mealService.getMeal(meal.getId());

        assertEquals(meal.getId(), foundMeal.id());
    }

    @Test
    void testGetMealNotExisted() {
        final Long mealId = 123L;

        when(mealRepository.findWithDetailsById(mealId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> mealService.getMeal(mealId));
    }

    @Test
    void testUpdateMeal() {
        final Meal meal = createTestMeal(1L, user, "2025-03-17T12:32:55");
        final MealRequest updateToExistedMeal = new MealRequest(
                LocalDateTime.now(),
                List.of(
                        new MealRequest.MealDishRequest(1L, 200L),
                        new MealRequest.MealDishRequest(1L, 200L),
                        new MealRequest.MealDishRequest(2L, 200L)
                )
        );

        when(dishService.tryFindDish(dish1.getId())).thenReturn(dish1);
        when(dishService.tryFindDish(dish2.getId())).thenReturn(dish2);
        when(mealRepository.findWithDetailsById(meal.getId())).thenReturn(Optional.of(meal));
        when(mealRepository.save(any(Meal.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mealMapper.toMealResponse(any(Meal.class)))
                .thenAnswer(invocation -> mapMealToMealResponse(invocation.getArgument(0)));

        final MealResponse updateMeal = mealService.updateMeal(meal.getId(), updateToExistedMeal);

        assertNotEquals(0, updateMeal.dishes().size());
        assertNotEquals(LocalDateTime.parse("2025-03-17T12:32:55"), updateMeal.mealTime());
    }

    private Meal createTestMeal(Long id, User user, String mealTime) {
        final Meal meal = new Meal();
        meal.setId(id);
        meal.setUser(user);
        meal.setMealTime(LocalDateTime.parse(mealTime));
        meal.setMealDishes(new ArrayList<>());
        return meal;
    }

    private BigDecimal calcDishCalsForPortion(Dish dish, Long portionSize) {
        return dish.getCaloriesPerPortion()
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(portionSize));
    }

    //TODO: дублировать логику маппера не очень хорошо,
    // но в данный момент нет возможности использовать настоящий маппер
    // так как в него необходимо инжектить вложеные мапперы
    private MealResponse mapMealToMealResponse(Meal meal) {
        final List<MealResponse.MealDishResponse> mealDishList = meal.getMealDishes().stream()
                .map(md -> new MealResponse.MealDishResponse(
                        md.getDish().getName(),
                        md.getPortionSize(),
                        calcDishCalsForPortion(md.getDish(), md.getPortionSize())
                )).toList();

        return new MealResponse(
                meal.getId(),
                meal.getMealTime(),
                mealDishList,
                meal.getTotalCalories()
        );
    }

}
