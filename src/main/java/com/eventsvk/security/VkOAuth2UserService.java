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

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User delegateUser = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (!"vkontakte".equals(registrationId)) {
            return delegateUser;
        }

        Map<String, Object> attributes = new HashMap<>(delegateUser.getAttributes());
        log.debug("VK raw attributes: {}", attributes);

        Object error = attributes.get("error");
        if (error != null) {
            throw new OAuth2AuthenticationException("VK error: " + error);
        }

        Object responseObj = attributes.get("response");
        if (responseObj instanceof List<?> list && !list.isEmpty()) {
            Object first = list.getFirst();
            if (first instanceof Map<?, ?> userMapRaw) {
                @SuppressWarnings("unchecked")
                Map<String, Object> userMap = (Map<String, Object>) userMapRaw;
                attributes.putAll(userMap);
                attributes.remove("response");
            } else {
                log.warn("VK response[0] is not a Map: {}", first);
            }
        } else {
            log.warn("VK response attribute has unexpected format: {}", responseObj);
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new DefaultOAuth2User(authorities, attributes, "id");
    }
}

