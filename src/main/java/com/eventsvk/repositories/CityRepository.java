package com.eventsvk.repositories;

import com.eventsvk.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    @Query("SELECT city.id FROM City city")
    List<Integer> findAllIds();
}
