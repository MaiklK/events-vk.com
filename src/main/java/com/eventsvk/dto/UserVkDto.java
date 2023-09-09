package com.eventsvk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserVkDto {
    @JsonProperty("id")
    private String vkid;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("bdate")
    private String birthdayDate;
    @JsonProperty("sex")
    private int sex;
    @JsonProperty("is_closed")
    private boolean isClosed;
    @JsonProperty("photo_id")
    private String photoId;
    @JsonProperty("home_phone")
    private String homePhone;
    @JsonProperty("city")
    private CityDto city;
    @JsonProperty("country")
    private CountryDto country;
    @JsonProperty("counters")
    private UserCountersDto counters;
    @JsonProperty("can_access_closed")
    private boolean canAccessClosed;
}

