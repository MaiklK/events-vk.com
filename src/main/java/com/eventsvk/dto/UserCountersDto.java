package com.eventsvk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCountersDto {
    @JsonProperty("albums")
    private int albums;
    @JsonProperty("audios")
    private int audios;
    @JsonProperty("followers")
    private int followers;
    @JsonProperty("friends")
    private int friends;
    @JsonProperty("gifts")
    private int gifts;
    @JsonProperty("groups")
    private int groups;
    @JsonProperty("pages")
    private int pages;
    @JsonProperty("photos")
    private int photos;
    @JsonProperty("videos")
    private int videos;
    @JsonProperty("clips_followers")
    private int clipsFollowers;
}
