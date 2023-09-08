package com.eventsvk.dto;

import com.eventsvk.entity.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountryDto extends Country {
    @JsonProperty("id")
    private long id;
    @JsonProperty("title")
    private String title;
}
