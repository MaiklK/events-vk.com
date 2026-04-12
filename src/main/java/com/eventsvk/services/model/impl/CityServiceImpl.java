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

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final Cache<Long, CityEntity> cityCache;

    @Override
    @Transactional
    public void upsertCity(Long id, String title) {
        if (id == null || isBlank(title)) {
            throw new IllegalArgumentException("ID города и название обязательны для заполнения и не могут быть null");
        }
        cityRepository.upsertCity(id, title);
    }

    @Override
    public Optional<CityEntity> findCityByIdOrGetFromCache(Long cityId) {
        if (cityId == null) {
            return Optional.empty();
        }

        CityEntity cached = cityCache.get(cityId, key -> getCityById(cityId).orElse(null));
        return Optional.ofNullable(cached);
    }

    public Optional<CityEntity> getCityById(Long cityId) {
        return cityRepository.findById(cityId);
    }
}
