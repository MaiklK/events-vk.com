package com.eventsvk.services.Impl;

import com.eventsvk.entity.City;
import com.eventsvk.repositories.CityRepository;
import com.eventsvk.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    @Transactional
    public void saveCity(City city) {
        cityRepository.save(city);
    }

    @Override
    @Transactional
    public void saveAllCity(List<City> cities) {
        cityRepository.saveAll(cities);
    }

    @Override
    public City findCityById(int cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }

    @Override
    public List<Integer> getAllCityId() {
        return cityRepository.findAllIds();
    }
}
