package com.eventsvk.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPersonalDto implements Serializable {
    private String inspiredBy;
    private String political;
    private String peopleMain;
    private String lifeMain;
    private String smoking;
    private String alcohol;
}
