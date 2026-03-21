package com.eventsvk.repositories;

import com.eventsvk.entity.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findByName(String name);
}
