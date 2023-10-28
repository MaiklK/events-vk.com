package com.eventsvk.services.User;

import com.eventsvk.entity.user.AccessToken;

import java.util.List;

public interface AccessTokenService {
    List<AccessToken> getAllTokens();

    void saveToken(AccessToken accessToken);

    void updateToken(AccessToken accessToken);

    AccessToken getTokenNotInUse();

    void setTokenNotValid(AccessToken accessToken);

    void setTokenInUse(AccessToken accessToken);

    void setTokenNotInUse(AccessToken accessToken);

    AccessToken getTokenById(String id);
}
