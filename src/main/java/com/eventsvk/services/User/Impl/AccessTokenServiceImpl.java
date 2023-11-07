package com.eventsvk.services.User.Impl;

import com.eventsvk.entity.user.AccessToken;
import com.eventsvk.repositories.AccessTokenRepository;
import com.eventsvk.services.User.AccessTokenService;
import com.eventsvk.util.exceptions.AccessTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {
    private final AccessTokenRepository tokenRepository;

    @Override
    public List<AccessToken> getAllTokens() {
        return tokenRepository.findAll();
    }

    @Override
    public AccessToken getTokenById(String id) {
        Optional<AccessToken> foundToken = tokenRepository.findById(id);
        try {
            return foundToken.orElseThrow(() ->
                    new AccessTokenNotFoundException("AccessToken с id: " + id + " не найден"));
        } catch (AccessTokenNotFoundException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public void saveToken(AccessToken accessToken) {
        AccessToken foundToken = getTokenById(accessToken.getId());
        if (!accessToken.equals(foundToken) && !foundToken.isInUse() || foundToken == null) {
            tokenRepository.save(accessToken);
        }
    }

    @Transactional
    @Override
    public void updateToken(AccessToken accessToken) {
        tokenRepository.save(accessToken);
    }

    @Transactional
    @Override
    public AccessToken getTokenNotInUse() {
        Optional<AccessToken> foundToken = tokenRepository.getRandomTokenNotInUse();
        try {
            return foundToken.orElseThrow(() ->
                    new AccessTokenNotFoundException("Свободный и валидный AccessToken не найден"));
        } catch (AccessTokenNotFoundException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public void setTokenNotValid(AccessToken accessToken) {
        accessToken.setValid(false);
        updateToken(accessToken);
    }

    @Transactional
    @Override
    public void setTokenInUse(AccessToken accessToken) {
        accessToken.setInUse(true);
        updateToken(accessToken);
    }

    @Transactional
    @Override
    public void setTokenNotInUse(AccessToken accessToken) {
        accessToken.setInUse(false);
        updateToken(accessToken);
    }
}
