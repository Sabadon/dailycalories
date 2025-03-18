package com.sabadon.dailycalories.dto.mapper;

import com.sabadon.dailycalories.dto.MealResponse;
import com.sabadon.dailycalories.entities.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = MealDishMapper.class)
public interface MealMapper {

    @Mapping(target = "dishes", source = "mealDishes")
    MealResponse toMealResponse(Meal meal);

}
