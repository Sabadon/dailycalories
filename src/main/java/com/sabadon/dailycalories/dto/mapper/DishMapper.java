package com.sabadon.dailycalories.dto.mapper;

import com.sabadon.dailycalories.dto.DishDto;
import com.sabadon.dailycalories.entities.Dish;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DishMapper {

    @Mapping(target = "id", ignore = true)
    Dish toEntity(DishDto dishDto);

    DishDto toDto(Dish dish);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    Dish partialUpdate(DishDto dishDto, @MappingTarget Dish dish);

}
