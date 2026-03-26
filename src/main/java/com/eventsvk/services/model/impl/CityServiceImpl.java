package com.eventsvk.services.model.impl;

import com.eventsvk.entity.CityEntity;
import com.eventsvk.repositories.CityRepository;
import com.eventsvk.services.model.CityService;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final Cache<Long, Optional<CityEntity>> cityCache;

    @Override
    @Transactional
    public void upsertCity(Long id, String title) {
        cityRepository.upsertCity(id, title);
    }

    @Override
    public Optional<CityEntity> findCityByIdOrGetFromCache(Long cityId) {
        return cityCache.get(cityId, key -> getCityById(cityId));
    }

    public Optional<CityEntity> getCityById(Long cityId) {
        return cityRepository.findById(cityId);
    }
}
