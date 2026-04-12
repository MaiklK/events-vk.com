package com.eventsvk.repositories;

import com.eventsvk.entity.event.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    //TODO создать сущность event создать методы поиска событий по дате и городу
}