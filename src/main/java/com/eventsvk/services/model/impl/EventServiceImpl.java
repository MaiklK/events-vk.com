package com.eventsvk.services.model.impl;

import com.eventsvk.dto.EventDto;
import com.eventsvk.repositories.EventRepository;
import com.eventsvk.services.model.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public List<EventDto> getEventsByDateAndCity(Long date, Long cityId) {
        return List.of();
    }
}
