package web.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import web.Model.Event;

import java.util.List;

@Repository
public class EventDaoImp implements EventDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addEvent(Event event) {
        entityManager.persist(event);
    }

    @Override
    public Event getEvent(long id) {
        return entityManager.find(Event.class, id);
    }

    @Override
    public List<Event> getAllEvents() {
        return entityManager.createQuery("from Event ", Event.class).getResultList();
    }

    @Override
    public void update(Event event, long id) {
        event.setId(id);
        entityManager.merge(event);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(getEvent(id));
    }
}
