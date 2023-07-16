package com.eventsvk.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class EventDaoImpl implements EventDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void deleteEventTable() {
        Query query = entityManager.createNativeQuery("drop table events;");
        query.executeUpdate();
    }

    public void clearEventTable() {
        Query query = entityManager.createNativeQuery("truncate table events");
        query.executeUpdate();
    }
    public void restartSequenceEvent() {
        Query query = entityManager.createNativeQuery("alter sequence seq_event restart");
        query.executeUpdate();
    }
}
