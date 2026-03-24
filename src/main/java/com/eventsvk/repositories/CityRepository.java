package com.eventsvk.repositories;

import com.eventsvk.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

    @Modifying
    @Query(
            value = "INSERT INTO cities (id, title) " +
                    "VALUES (:id, :title) " +
                    "ON CONFLICT (id) DO NOTHING",
            nativeQuery = true
    )
    void upsertCity(@Param("id") Long id, @Param("title") String title);
}
