package web.Dao;

import web.Model.Event;

import java.util.List;

public interface EventDao {

    void addEvent(Event event);
    Event getEvent(int id);
    List<Event> getAllEvents();
}
