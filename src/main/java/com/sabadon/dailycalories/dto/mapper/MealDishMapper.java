package com.sabadon.dailycalories.dto.mapper;

import com.sabadon.dailycalories.dto.MealResponse;
import com.sabadon.dailycalories.entities.MealDish;
import com.sabadon.dailycalories.util.MealUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", imports = MealUtil.class)
public interface MealDishMapper {

    @Mapping(target = "name", source = "dish.name")
    @Mapping(target = "calories", expression = "java(MealUtil.calculateMealDishCalories(mealDish))")
    MealResponse.MealDishResponse toMealDishResponse(MealDish mealDish);


}
