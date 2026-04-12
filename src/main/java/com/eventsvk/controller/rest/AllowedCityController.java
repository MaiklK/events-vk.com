package com.eventsvk.controller.rest;

import com.eventsvk.dto.AllowedCityDto;
import com.eventsvk.services.model.AllowedCityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/allowedCity")
@RequiredArgsConstructor
public class AllowedCityController {
    private final AllowedCityService allowedCityService;

    @GetMapping("/{id}")
    public AllowedCityDto getAllowedCityById(@PathVariable Long id) {
        return allowedCityService.getAllowedCityById(id);
    }

    @GetMapping
    public List<AllowedCityDto> getAllAllowedCities() {
        return allowedCityService.getAllAllowedCity();
    }

    @PostMapping("/add")
    public void addAllowedCity(Principal principal,
                        @RequestParam Long cityId,
                        @RequestParam Long countryId,
                        @RequestParam String cityName) {
        AllowedCityDto allowedCityDto = AllowedCityDto.builder()
                .cityId(cityId)
                .countryId(countryId)
                .cityName(cityName)
                .build();
        allowedCityService.addAllowedCity(allowedCityDto, principal.getName());
    }
}
