package com.eventsvk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserVkDto {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("id")
    private String id;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("deactivated")
    private String deactivated;
    @JsonProperty("is_closed")
    private boolean isClosed;
    @JsonProperty("bdate")
    private String bdate;
    @JsonProperty("sex")
    private int sex;
    @JsonProperty("city")
    private CityDto city;
    @JsonProperty("country")
    private CountryDto country;
    @JsonProperty("counters")
    private UserCountersDto counters;
    @JsonProperty("photo_id")
    private String photoId;
}

