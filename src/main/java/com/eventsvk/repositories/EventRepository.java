package com.eventsvk.repositories;

import com.eventsvk.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
public interface EventRepository extends JpaRepository<Event, Long> {
}