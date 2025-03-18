package com.sabadon.dailycalories.dto.mapper;

import com.sabadon.dailycalories.dto.meal.MealResponse;
import com.sabadon.dailycalories.dto.report.DailyReportResponse;
import com.sabadon.dailycalories.entities.Meal;
import com.sabadon.dailycalories.entities.MealDish;
import com.sabadon.dailycalories.util.MealUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {MealMapper.MealDishItemMapper.class, MealMapper.MealDishMapper.class})
public interface MealMapper {

    @Mapping(target = "dishes", source = "mealDishes")
    MealResponse toMealResponse(Meal meal);

    DailyReportResponse.MealItem toMealItem(MealResponse response);

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
    interface MealDishItemMapper {
        DailyReportResponse.MealDishItem toMealDishItem(MealResponse.MealDishResponse mealDishResponse);
    }

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", imports = MealUtil.class)
    interface MealDishMapper {
        @Mapping(target = "name", source = "dish.name")
        @Mapping(target = "calories", expression = "java(MealUtil.calculateMealDishCalories(mealDish))")
        MealResponse.MealDishResponse toMealDishResponse(MealDish mealDish);
    }

}
