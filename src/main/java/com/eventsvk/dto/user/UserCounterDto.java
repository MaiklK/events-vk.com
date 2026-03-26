package com.eventsvk.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCounterDto implements Serializable {
    private int albums;
    private int audios;
    private int followers;
    private int friends;
    private int gifts;
    private int groups;
    private int pages;
    private int photos;
    private int videos;
    private Long clipsFollowers;

}
