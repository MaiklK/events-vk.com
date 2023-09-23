package com.eventsvk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventDto {
    @JsonProperty("id")
    private int eventVkid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("screen_name")
    private String screenName;
    @JsonProperty("is_closed")
    private String isClosed;
    @JsonProperty("type")
    private String type;
    @JsonProperty("deactivated")
    private String deactivated;
    @JsonProperty("photo_200")
    private String photo200;
}
