package com.eventsvk.services;

import com.eventsvk.entity.Country;

import java.util.List;

public interface CountryService {
    void saveCountry(Country country);

    void saveAllCountry(List<Country> countries);

    Country findCountryById(int countryId);

    List<Integer> getAllCountryId();
}