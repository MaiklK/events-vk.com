package web.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.Dao.EventDao;
import web.Model.Event;

import java.util.List;

@Service
@Transactional
public class EventServiceImp implements EventService {

    private final EventDao eventDao;

    public EventServiceImp(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public void addUser(Event event) {
        eventDao.addEvent(event);
    }

    @Override
    public Event getEvent(int id) {
        return eventDao.getEvent(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventDao.getAllEvents();
    }
}
