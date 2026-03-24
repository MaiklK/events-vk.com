package com.eventsvk.security;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Кастомный клиент для разбора нестандартного ответа VK от /access_token.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VkAccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private final static String USER_ID = "user_id";
    private final static String EMAIL = "email";
    private final static String OAUTH_VK_ERROR = "Ошибка авторизации ВК: ";

    private final VkApiClient vkApiClient;

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {

        UserAuthResponse authResponse = getAuthResponse(authorizationGrantRequest);

        String accessToken = authResponse.getAccessToken();
        Integer expiresIn = authResponse.getExpiresIn();

        Map<String, Object> additionalParams = new HashMap<>();
        additionalParams.put(USER_ID, authResponse.getUserId());
        if (authResponse.getEmail() != null) {
            additionalParams.put(EMAIL, authResponse.getEmail());
        }

        return OAuth2AccessTokenResponse.withToken(accessToken)
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .expiresIn(expiresIn)
                .scopes(Collections.emptySet())
                .additionalParameters(additionalParams)
                .build();
    }

    private UserAuthResponse getAuthResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        var clientRegistration = authorizationGrantRequest.getClientRegistration();
        var authorizationResponse = authorizationGrantRequest.getAuthorizationExchange().getAuthorizationResponse();
        var code = authorizationResponse.getCode();
        var clientId = clientRegistration.getClientId();
        var clientSecret = clientRegistration.getClientSecret();
        var redirectUri = authorizationResponse.getRedirectUri();

        try {
            return vkApiClient.oAuth()
                    .userAuthorizationCodeFlow(Integer.valueOf(clientId), clientSecret, redirectUri, code)
                    .execute();

        } catch (ApiException | ClientException e) {
            log.error(OAUTH_VK_ERROR + "{}", e.getMessage());
            throw new OAuth2AuthorizationException(
                    new OAuth2Error(OAUTH_VK_ERROR,
                            "Не удалось получить токен: " + e.getMessage(), null),
                    e);
        }
    }
}
