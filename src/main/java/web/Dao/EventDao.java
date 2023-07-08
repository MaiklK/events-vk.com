package web.Dao;

import web.Model.Event;

import java.util.List;

public interface EventDao {

    void addEvent(Event event);
    Event getEvent(long id);
    List<Event> getAllEvents();
    void update(Event event, long id);
    void delete(long id);
}
