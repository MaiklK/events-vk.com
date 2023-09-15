package com.eventsvk.services;

import com.eventsvk.entity.City;

import java.util.List;

public interface CityService {
    void saveAllCity(List<City> cities);

    void saveCity(City city);

    City findCityById(int cityId);

    List<Integer> getAllCityId();

}
