package com.eventsvk.services;

import com.eventsvk.entity.AccessTokenEntity;
import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.services.model.AccessTokenService;
import com.eventsvk.services.model.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VkPostAuthService {

    private final UserService userService;
    private final AccessTokenService accessTokenService;

    @Transactional
    public void saveUserAndToken(UserEntity user, AccessTokenEntity accessToken) {
        userService.saveUser(user);
        accessTokenService.saveAccessToken(accessToken);
    }
}
