package com.sabadon.dailycalories.dto.mapper;

import com.sabadon.dailycalories.dto.report.DailyHistoryResponse;
import com.sabadon.dailycalories.repository.MealRepository;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DailyHistoryMapper {

    DailyHistoryResponse.DailyTotal toDailyTotal(MealRepository.DailyCaloriesTotal total);

}
