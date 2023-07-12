package com.events.eventsvk.com.dao;

import com.events.eventsvk.com.models.Event;

import java.util.List;

public interface EventDao {

    void addEvent(Event event);

    Event getEvent(long id);

    List<Event> getAllEvents();

    void update(long id, Event event);

    void delete(long id);
}
