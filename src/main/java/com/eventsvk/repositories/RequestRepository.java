package com.eventsvk.repositories;

import com.eventsvk.entity.event.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {
}
