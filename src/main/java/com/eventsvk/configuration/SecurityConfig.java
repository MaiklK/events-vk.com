package com.eventsvk.configuration;

import com.eventsvk.security.JwtAuthenticationFilter;
import com.eventsvk.security.VkAccessTokenResponseClient;
import com.eventsvk.security.VkOAuth2SuccessHandler;
import com.eventsvk.security.VkOAuth2UserService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.eventsvk.constant.SecurityConstant.AUTH_COOKIE_NAME;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final VkOAuth2UserService vkOAuth2UserService;
    private final VkAccessTokenResponseClient vkAccessTokenResponseClient;
    private final VkOAuth2SuccessHandler vkOAuth2SuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/webjars/**", "/error").permitAll()
                        .requestMatchers("/admin**").hasRole("ADMIN")
                        .requestMatchers("/api/v1**").hasRole("ADMIN")
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
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .addLogoutHandler((request, response, authentication) -> {
                            Cookie cookie = new Cookie(AUTH_COOKIE_NAME, "");
                            cookie.setPath("/");
                            cookie.setHttpOnly(true);
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        })
                        .permitAll()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}