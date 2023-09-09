package com.eventsvk.repositories;

import com.eventsvk.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByVkid(String userVkid);
}