package com.eventsvk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountryDto {
    @JsonProperty("id")
    private long countryId;
    @JsonProperty("title")
    private String countryName;
}
