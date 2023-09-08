package com.eventsvk.repositories;

import com.eventsvk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByVkid(String userVkid);
}