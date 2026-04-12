package com.eventsvk.controller.rest;

import com.eventsvk.dto.AllowedCityDto;
import com.eventsvk.services.model.AllowedCityService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/allowedCity")
@RequiredArgsConstructor
public class AllowedCityController {
    private final AllowedCityService allowedCityService;

    @GetMapping("/{id}")
    public AllowedCityDto getAllowedCityById(@PathVariable @Min(1L) @Positive Long id) {
        return allowedCityService.getAllowedCityById(id);
    }

    @GetMapping
    public List<AllowedCityDto> getAllAllowedCities() {
        return allowedCityService.getAllAllowedCity();
    }

    @PostMapping("/add")
    public void addAllowedCity(Principal principal,
                        @RequestParam @Min(1L) @Positive Long cityId,
                        @RequestParam @Min(1L) @Positive Long countryId,
                        @RequestParam @NotEmpty String cityName) {
        AllowedCityDto allowedCityDto = AllowedCityDto.builder()
                .cityId(cityId)
                .countryId(countryId)
                .cityName(cityName)
                .build();
        allowedCityService.addAllowedCity(allowedCityDto, principal.getName());
    }
}
