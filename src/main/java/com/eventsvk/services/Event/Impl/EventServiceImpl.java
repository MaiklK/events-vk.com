package com.eventsvk.services.Event.Impl;

import com.eventsvk.entity.event.Event;
import com.eventsvk.repositories.EventRepository;
import com.eventsvk.services.Event.EventService;
import com.eventsvk.util.exceptions.EventNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    @Override
    @Transactional
    public void saveAllEvents(List<Event> eventList) {
        eventRepository.saveAll(eventList);
    }

    @Transactional


    @Override
    public Event findEventByUuid(String eventUuid) {
        Optional<Event> foundEvent = eventRepository.findById(eventUuid);

        return foundEvent.orElseThrow(() ->
                new EventNotFoundException("Событие с eventUuid: " + eventUuid + "не найдено"));
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    @Transactional
    public void updateEvent(Event updateEvent, String eventUuid) {
        updateEvent.setUuid(eventUuid);
        eventRepository.save(updateEvent);
    }
}

