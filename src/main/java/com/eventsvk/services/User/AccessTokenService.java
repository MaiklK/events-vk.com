package com.eventsvk.services.User;

import com.eventsvk.entity.user.AccessToken;
import com.eventsvk.util.exceptions.AccessTokenNotFoundException;

import java.util.List;

public interface AccessTokenService {
    List<AccessToken> getAllTokens();

    void saveToken(AccessToken accessToken) throws AccessTokenNotFoundException;

    void updateToken(AccessToken accessToken);

    AccessToken getTokenNotInUse() throws AccessTokenNotFoundException;

    void setTokenNotValid(AccessToken accessToken);

    void setTokenStatus(AccessToken accessToken, boolean status);

    AccessToken getTokenById(String id) throws AccessTokenNotFoundException;
}
