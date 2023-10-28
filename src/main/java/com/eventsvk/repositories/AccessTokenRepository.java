package com.eventsvk.repositories;

import com.eventsvk.entity.user.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM access_tokens WHERE is_in_use = false" +
            " AND is_valid = true ORDER BY random() LIMIT 1")
    Optional<AccessToken> getRandomTokenNotInUse();
}
