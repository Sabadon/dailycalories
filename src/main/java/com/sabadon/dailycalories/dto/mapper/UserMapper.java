package com.sabadon.dailycalories.dto.mapper;

import com.sabadon.dailycalories.entities.User;
import com.sabadon.dailycalories.dto.user.UserDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    //Запрещаем модифицировать ID и Daily Calories
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dailyCalories", ignore = true)
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dailyCalories", ignore = true)
    User partialUpdate(UserDto userDto, @MappingTarget User user);

}
