package com.eventsvk.services.model.impl;

import com.eventsvk.entity.AccessTokenEntity;
import com.eventsvk.repositories.AccessTokenRepository;
import com.eventsvk.services.model.AccessTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {
    private final AccessTokenRepository accessTokenRepository;

    @Override
    public void saveAccessToken(AccessTokenEntity accessToken) {
        accessTokenRepository.save(accessToken);
        log.debug("AccessToken для id: {}, сохранен", accessToken.getId());
    }

    @Override
    public Optional<AccessTokenEntity> findAccessTokenByUserId(Long userVkId) {
        return accessTokenRepository.findById(userVkId);
    }
}
