package com.eventsvk.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.net.URI;

@Getter
@Setter
@SuperBuilder
public class EventDto {
    private String name;
    private String screenName;
    private int isClosed;
    private String type;
    private URI photo50;
    private URI photo100;
    private URI photo200;
}
