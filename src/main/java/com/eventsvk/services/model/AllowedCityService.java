package com.eventsvk.services.model;

import com.eventsvk.dto.AllowedCityDto;

import java.util.List;

public interface AllowedCityService {
    AllowedCityDto getAllowedCityById(Long id);

    List<AllowedCityDto> getAllAllowedCity();

    void addAllowedCity(AllowedCityDto allowedCityEntity, String userId);
}
