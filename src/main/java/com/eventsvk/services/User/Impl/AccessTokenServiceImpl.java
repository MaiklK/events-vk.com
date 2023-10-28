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
    public List<AccessToken> getNotInUseTokens() {
        return tokenRepository.findAll();
    }

    @Override
    public void saveToken(AccessToken accessToken) {
        tokenRepository.save(accessToken);
    }

    @Override
    public void updateToken(AccessToken accessToken) {
        tokenRepository.save(accessToken);
    }

    @Override
    public AccessToken getTokenNotInUse() {
        Optional<AccessToken> foundToken = tokenRepository.findByIsIAndInUseAndValid(true);
        return foundToken.orElseThrow(() ->
                new AccessTokenNotFoundException("Свободный и валидный AccessToken не найден"));
    }

    @Override
    public void setTokenNotValid(AccessToken accessToken) {
        accessToken.setValid(false);
        tokenRepository.save(accessToken);
    }

    @Override
    public void setTokenInUse(AccessToken accessToken) {
        accessToken.setInUse(true);
        tokenRepository.save(accessToken);
    }

    @Override
    public void setTokenNotInUse(AccessToken accessToken) {
        accessToken.setInUse(false);
        tokenRepository.save(accessToken);
    }
}
