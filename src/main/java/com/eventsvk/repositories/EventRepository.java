package com.eventsvk.repositories;

import com.eventsvk.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
    //TODO создать сущность event создать методы поиска событий по дате и городу
}