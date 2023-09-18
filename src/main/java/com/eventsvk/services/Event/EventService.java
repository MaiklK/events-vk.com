package com.eventsvk.services.Event;

import com.eventsvk.entity.event.Event;

import java.util.List;

public interface EventService {

    void saveEvent(Event event);

    Event findEventByUuid(String eventUuid);

    List<Event> getAllEvents();

    void updateEvent(Event event, String eventUuid);
}
