package com.eventsvk.services;

import com.eventsvk.models.Event;

import java.util.List;

public interface EventService {

    void saveEvent(Event event);

    Event findEventById(long id);

    List<Event> findAllEvents();

    void updateEvent(Event event, long id);

    void deleteEvent(long id);

    public void deleteEventTable();

    public void clearEventTable();

    public void restartSequenceEvent();
}
