package com.eventsvk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CityDto {
    @JsonProperty("id")
    private long cityId;
    @JsonProperty("title")
    private String cityName;
}
