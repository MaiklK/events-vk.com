package com.eventsvk.repositories;

import com.eventsvk.entity.user.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
    Optional<AccessToken> findByIsIAndInUseAndValid(boolean is);
}
