package com.sabadon.dailycalories.dto.mapper;

import com.sabadon.dailycalories.domain.User;
import com.sabadon.dailycalories.dto.UserDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    User partialUpdate(UserDto channelDto, @MappingTarget User channel);

}
