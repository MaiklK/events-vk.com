package com.eventsvk.repositories;

import com.eventsvk.entity.event.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<Request, Integer> {
}
