package com.eventsvk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPersonalDto {
    @JsonProperty("political")
    private int political;
    @JsonProperty("inspired_by")
    private String inspiredBy;
    @JsonProperty("people_main")
    private int peopleMain;
    @JsonProperty("life_main")
    private int lifeMain;
    @JsonProperty("smoking")
    private int smoking;
    @JsonProperty("alcohol")
    private int alcohol;
    @JsonProperty("langs")
    private String[] langs;
}
