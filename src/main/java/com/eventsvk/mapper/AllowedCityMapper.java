package com.eventsvk.mapper;

import com.eventsvk.dto.AllowedCityDto;
import com.eventsvk.entity.event.AllowedCityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AllowedCityMapper {
    AllowedCityDto fromEntityToDto(AllowedCityEntity allowedCityEntity);

    AllowedCityEntity fromDtoToEntity(AllowedCityDto allowedCityDto);
}
