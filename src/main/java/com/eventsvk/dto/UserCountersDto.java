package com.eventsvk.dto;

import com.eventsvk.entity.UserCounters;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCountersDto extends UserCounters {
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
    @JsonProperty("subscriptions")
    private int subscriptions;
    @JsonProperty("videos")
    private int videos;
    @JsonProperty("new_photo_tags")
    private int newPhotoTags;
    @JsonProperty("new_recognition_tags")
    private int newRecognitionTags;
    @JsonProperty("clips_followers")
    private int clipsFollowers;
}
