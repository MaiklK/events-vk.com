package com.eventsvk.repositories;

import com.eventsvk.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    @Query("SELECT region.id FROM Region region")
    List<Integer> findAllIds();
}
