package com.eventsvk.repositories;

import com.eventsvk.entity.user.UserCounters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCountersRepository extends JpaRepository<UserCounters, Long> {
}
