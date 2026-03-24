package com.eventsvk.services.model.impl;

import com.eventsvk.repositories.CityRepository;
import com.eventsvk.services.model.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    @Transactional
    public void upsertCity(Long id, String title) {
        cityRepository.upsertCity(id, title);
    }
}
