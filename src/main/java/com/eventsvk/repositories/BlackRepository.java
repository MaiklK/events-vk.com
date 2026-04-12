package com.eventsvk.repositories;

import com.eventsvk.entity.event.BlackListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackRepository extends JpaRepository<BlackListEntity, Long> {
}
