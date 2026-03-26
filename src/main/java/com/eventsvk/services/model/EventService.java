package com.eventsvk.services.model;

import com.eventsvk.dto.EventDto;

import java.util.List;

public interface EventService {
    List<EventDto> getEventsByDateAndCity(Long date, Long cityId);
}
