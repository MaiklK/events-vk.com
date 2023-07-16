package com.eventsvk.dao;

public interface EventDao {

    public void deleteEventTable();

    public void clearEventTable();

    public void restartSequenceEvent();
}
