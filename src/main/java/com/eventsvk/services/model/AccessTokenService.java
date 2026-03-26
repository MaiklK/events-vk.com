package com.eventsvk.services.model;

import com.eventsvk.entity.AccessTokenEntity;

import java.util.Optional;

public interface AccessTokenService {
    void saveAccessToken(AccessTokenEntity accessToken);
    Optional<AccessTokenEntity> findAccessTokenByUserId(Long userVkId);
}
