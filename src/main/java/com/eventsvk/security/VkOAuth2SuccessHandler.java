package com.eventsvk.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import java.io.IOException;

import static com.eventsvk.constant.SecurityConstant.AUTH_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class VkOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final static String VKONTAKTE = "vkontakte";
    private final static String REDIRECT_URI = "/";

    private final VkPostAuthProcessor vkPostAuthProcessor;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            String username = oauthToken.getName();
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                String token = jwtService.generateToken(userDetails);
                Cookie cookie = new Cookie(AUTH_COOKIE_NAME, token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                // cookie.setSecure(true); // включить при работе по HTTPS
                cookie.setMaxAge((int) jwtService.getExpirationSeconds());
                response.addCookie(cookie);
            } catch (Exception ex) {
                // если не удалось загрузить пользователя или сгенерировать токен, продолжаем без JWT
            }

            String registrationId = oauthToken.getAuthorizedClientRegistrationId();
            if (VKONTAKTE.equals(registrationId)) {
                OAuth2User oAuth2User = oauthToken.getPrincipal();

                OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                        registrationId,
                        oauthToken.getName()
                );

                if (client != null) {
                    OAuth2AccessToken accessToken = client.getAccessToken();
                    if (accessToken != null) {
                        vkPostAuthProcessor.saveUserAfterAuthorization(oAuth2User, accessToken);
                    }
                }
            }
        }

        response.sendRedirect(REDIRECT_URI);
    }
}
