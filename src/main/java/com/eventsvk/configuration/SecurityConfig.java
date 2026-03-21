package com.eventsvk.configuration;

import com.eventsvk.security.VkAccessTokenResponseClient;
import com.eventsvk.security.VkOAuth2SuccessHandler;
import com.eventsvk.security.VkOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final VkOAuth2UserService vkOAuth2UserService;
    private final VkAccessTokenResponseClient vkAccessTokenResponseClient;
    private final VkOAuth2SuccessHandler vkOAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/webjars/**", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .tokenEndpoint(token -> token
                                .accessTokenResponseClient(vkAccessTokenResponseClient)
                        )
                        .userInfoEndpoint(userInfo -> userInfo.userService(vkOAuth2UserService))
                        .successHandler(vkOAuth2SuccessHandler)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
        return http.build();
    }
}