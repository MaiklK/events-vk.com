package com.eventsvk.services.User.Impl;

import com.eventsvk.entity.user.AccessToken;
import com.eventsvk.repositories.AccessTokenRepository;
import com.eventsvk.services.User.AccessTokenService;
import com.eventsvk.util.exceptions.AccessTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor()
@Slf4j
public class AccessTokenServiceImpl implements AccessTokenService {
    private final AccessTokenRepository tokenRepository;
    private final AccessTokenService self;

    @Transactional(readOnly = true)
    @Override
    public List<AccessToken> getAllTokens() {
        return tokenRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public AccessToken getTokenById(String id) throws AccessTokenNotFoundException {
        Optional<AccessToken> foundToken = tokenRepository.findById(id);
        return foundToken.orElseThrow(() -> {
            log.error("AccessToken с id: {} не найден", id);
            return new AccessTokenNotFoundException("AccessToken с id: " + id + " не найден");
        });
    }

    @Transactional
    @Override
    public void saveToken(AccessToken accessToken) throws AccessTokenNotFoundException {
        AccessToken foundToken = self.getTokenById(accessToken.getId());
        if (!accessToken.equals(foundToken) && !foundToken.isInUse()) {
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
    public AccessToken getTokenNotInUse() throws AccessTokenNotFoundException {
        Optional<AccessToken> foundToken = tokenRepository.getRandomTokenNotInUse();

        return foundToken.orElseThrow(() -> {
            log.error("Свободный и валидный AccessToken не найден");
            return new AccessTokenNotFoundException("Свободный и валидный AccessToken не найден");
        });

    }

    @Override
    public void setTokenNotValid(AccessToken accessToken) {
        accessToken.setValid(false);
        self.updateToken(accessToken);
    }

    @Override
    public void setTokenStatus(AccessToken accessToken, boolean status) {
        accessToken.setInUse(status);
        self.updateToken(accessToken);
    }
}
