package com.eventsvk.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFullDto extends UserDto implements Serializable {
    private String city;
    private UserCounterDto counterDto;
    private UserPersonalDto personalDto;
}
