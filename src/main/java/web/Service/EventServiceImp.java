package web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.Dao.EventDao;
import web.Model.Event;

import java.util.List;

@Service
@Transactional
public class EventServiceImp implements EventService {

    private final EventDao eventDao;

    @Autowired
    public EventServiceImp(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public void addEvent(Event event) {
        eventDao.addEvent(event);
    }

    @Override
    public Event getEvent(long id) {
        return eventDao.getEvent(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventDao.getAllEvents();
    }

    @Override
    public void update(Event event, long id) {
        eventDao.update(id, event);
    }

    @Override
    public void delete(long id) {
        eventDao.delete(id);
    }
}
