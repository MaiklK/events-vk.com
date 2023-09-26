package com.eventsvk.services.Impl;

import com.eventsvk.entity.Country;
import com.eventsvk.repositories.CountryRepository;
import com.eventsvk.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    @Transactional
    public void saveCountry(Country country) {
        countryRepository.save(country);
    }

    @Override
    @Transactional
    public void saveAllCountry(List<Country> countries) {
        countryRepository.saveAll(countries);
    }

    @Override
    public Country findCountryById(int countryId) {
        return countryRepository.findById(countryId).orElse(null);
    }

    @Override
    public List<Integer> getAllCountryId() {
        return countryRepository.findAllIds();
    }
}