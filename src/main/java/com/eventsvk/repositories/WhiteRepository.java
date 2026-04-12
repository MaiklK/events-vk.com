package com.eventsvk.repositories;

import com.eventsvk.entity.event.WhiteListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhiteRepository extends JpaRepository<WhiteListEntity, Long> {
}
