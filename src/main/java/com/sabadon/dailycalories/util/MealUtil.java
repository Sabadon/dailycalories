package com.sabadon.dailycalories.util;

import com.sabadon.dailycalories.entities.MealDish;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MealUtil {

    public static BigDecimal calculateMealDishCalories(MealDish md) {
        return md.getDish().getCaloriesPerPortion()
                .divide(BigDecimal.valueOf(100),4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(md.getPortionSize()));
    }

}
