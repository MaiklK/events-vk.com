package com.eventsvk.services;

import com.eventsvk.dao.EventDao;
import com.eventsvk.models.Event;
import com.eventsvk.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventDao eventDao;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, EventDao eventDao) {
        this.eventRepository = eventRepository;
        this.eventDao = eventDao;
    }

    @Override
    @Transactional
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    @Override
    public Event findEventById(long id) {
        Optional<Event> foundEvent = eventRepository.findById(id);

        return foundEvent.orElse(null);
    }

    @Override
    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    @Transactional
    public void updateEvent(Event updateEvent, long id) {
        updateEvent.setId(id);
        eventRepository.save(updateEvent);
    }

    @Override
    @Transactional
    public void deleteEvent(long id) {
        eventRepository.delete(findEventById(id));
    }

    @Override
    @Transactional
    public void deleteEventTable() {
        eventDao.deleteEventTable();
    }

    @Override
    @Transactional
    public void clearEventTable() {
        eventDao.clearEventTable();
    }

    @Transactional

    @Override
    public void restartSequenceEvent() {
        eventDao.restartSequenceEvent();
    }
}
