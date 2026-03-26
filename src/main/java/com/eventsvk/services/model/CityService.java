package com.eventsvk.services.model;

import com.eventsvk.entity.CityEntity;

import java.util.Optional;

public interface CityService {
    void upsertCity(Long id, String title);
    Optional<CityEntity> findCityByIdOrGetFromCache(Long cityId);
}
