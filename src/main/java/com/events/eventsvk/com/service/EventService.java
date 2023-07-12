package com.events.eventsvk.com.service;

import com.events.eventsvk.com.models.Event;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(long id);

    List<Event> getAllEvents();

    void update(Event event, long id);

    void delete(long id);
}
