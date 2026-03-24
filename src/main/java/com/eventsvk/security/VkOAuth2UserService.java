package com.eventsvk.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис обработки авторизацию через VK OAuth2
 */
@Slf4j
@Service
public class VkOAuth2UserService extends DefaultOAuth2UserService {

    private final static String ID = "id";
    private final static String ERROR = "error";
    private final static String ROLE_USER = "ROLE_USER";
    private final static String RESPONSE = "response";
    private final static String VKONTAKTE = "vkontakte";
    private final static String AUTHORIZATION_ERROR = "Ошибка авторизации через VK: ";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        if (!VKONTAKTE.equals(userRequest.getClientRegistration().getRegistrationId())) {
            return oAuth2User;
        }

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

        Object error = attributes.get(ERROR);
        if (error != null) {
            log.error(AUTHORIZATION_ERROR + "{}", error);
            throw new OAuth2AuthenticationException(AUTHORIZATION_ERROR + error);
        }

        mergeVkResponseAttributes(attributes);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(ROLE_USER));
        return new DefaultOAuth2User(authorities, attributes, ID);
    }

    @SuppressWarnings("unchecked")
    private void mergeVkResponseAttributes(Map<String, Object> attributes) {
        var response = attributes.get(RESPONSE);
        if (!(response instanceof List<?> list) || list.isEmpty()) {
            log.warn("Атрибут VK 'response' имеет неожиданный формат: {}", response);
            return;
        }

        var firstElement = list.getFirst();
        if (!(firstElement instanceof Map<?, ?> userMapRaw)) {
            log.warn("VK response[0] не является Map: {}", firstElement);
            return;
        }

        var userMap = (Map<String, Object>) userMapRaw;
        attributes.putAll(userMap);
        attributes.remove(RESPONSE);
    }
}

