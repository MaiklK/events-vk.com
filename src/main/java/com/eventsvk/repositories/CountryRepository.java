package com.eventsvk.repositories;

import com.eventsvk.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query("SELECT country.id FROM Country country")
    List<Integer> findAllIds();
}
