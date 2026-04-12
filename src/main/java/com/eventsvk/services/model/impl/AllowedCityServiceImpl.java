package com.eventsvk.services.model.impl;

import com.eventsvk.dto.AllowedCityDto;
import com.eventsvk.entity.event.AllowedCityEntity;
import com.eventsvk.exception.AllowedCityNotFound;
import com.eventsvk.mapper.AllowedCityMapper;
import com.eventsvk.repositories.AllowedCityRepository;
import com.eventsvk.services.model.AllowedCityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AllowedCityServiceImpl implements AllowedCityService {
    private final AllowedCityRepository allowedCityRepository;
    private final AllowedCityMapper allowedCityMapper;

    @Override
    public AllowedCityDto getAllowedCityById(Long id) {
        return allowedCityRepository.findById(id)
                .map(allowedCityMapper::fromEntityToDto)
                .orElseThrow(
                        () -> {
                            log.error("Allowed city with id {} not found", id);
                            return new AllowedCityNotFound("Allowed city with id " + id + " not found");
                        });
    }

    @Override
    public List<AllowedCityDto> getAllAllowedCity() {
        return allowedCityRepository.findAll()
                .stream()
                .map(allowedCityMapper::fromEntityToDto)
                .toList();
    }

    @Override
    public void addAllowedCity(AllowedCityDto allowedCityDto, String userId) {
        AllowedCityEntity allowedCity = allowedCityMapper.fromDtoToEntity(allowedCityDto);
        allowedCityRepository.save(allowedCity);

        log.info("User with id {} added allowed city {} to the database", userId, allowedCity);
    }
}
