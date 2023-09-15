package com.eventsvk.repositories;

import com.eventsvk.entity.user.UserPersonal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPersonalRepository extends JpaRepository<UserPersonal, Long> {
}
