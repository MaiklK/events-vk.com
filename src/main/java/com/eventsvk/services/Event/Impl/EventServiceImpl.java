package com.eventsvk.services.Event.Impl;

import com.eventsvk.entity.event.Event;
import com.eventsvk.repositories.EventRepository;
import com.eventsvk.services.Event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    @Override
    public Event findEventByUuid(String eventUuid) {
        Optional<Event> foundEvent = eventRepository.findById(eventUuid);

        return foundEvent.orElse(null);
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
