package web.Service;

import web.Model.Event;

import java.util.List;

public interface EventService {

    void addUser(Event event);

    Event getEvent(int id);

    List<Event> getAllEvents();
}
