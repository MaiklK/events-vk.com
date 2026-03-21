package com.eventsvk.security;

import com.eventsvk.util.ExtractUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Кастомный клиент для разбора нестандартного ответа VK от /access_token.
 */
@Slf4j
@Component
public class VkAccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        ClientRegistration clientRegistration = authorizationGrantRequest.getClientRegistration();
        MultiValueMap<String, String> formParameters = buildFormParameters(authorizationGrantRequest, clientRegistration);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formParameters, headers);

        var response = restTemplate.exchange(
                clientRegistration.getProviderDetails().getTokenUri(),
                HttpMethod.POST,
                entity,
                Map.class
        );

        @SuppressWarnings("unchecked")
        Map<String, Object> body = response.getBody();
        if (body == null) {
            throw new IllegalStateException("VK token response body is null");
        }

        String accessToken = ExtractUtil.extractRequiredString(body);
        long expiresIn = ExtractUtil.extractLong(body.get("expires_in"));

        Map<String, Object> additionalParameters = new HashMap<>(body);

        return OAuth2AccessTokenResponse.withToken(accessToken)
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .expiresIn(expiresIn)
                .scopes(Collections.emptySet())
                .additionalParameters(additionalParameters)
                .build();
    }

    private static MultiValueMap<String, String> buildFormParameters(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest,
                                                                     ClientRegistration clientRegistration) {
        var exchange = authorizationGrantRequest.getAuthorizationExchange();

        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
        formParameters.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        formParameters.add(OAuth2ParameterNames.CODE, exchange.getAuthorizationResponse().getCode());
        formParameters.add(OAuth2ParameterNames.REDIRECT_URI, exchange.getAuthorizationRequest().getRedirectUri());
        formParameters.add(OAuth2ParameterNames.CLIENT_ID, clientRegistration.getClientId());
        formParameters.add(OAuth2ParameterNames.CLIENT_SECRET, clientRegistration.getClientSecret());
        return formParameters;
    }
}
