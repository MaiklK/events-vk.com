package com.eventsvk.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AllowedCityDto {
    private Long cityId;
    private Long countryId;
    private String cityName;
}
