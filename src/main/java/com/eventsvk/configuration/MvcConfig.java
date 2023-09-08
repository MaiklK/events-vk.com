package com.eventsvk.configuration;

import com.eventsvk.services.AuthVkService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {
    private final AuthVkService authVkService;
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/index").setViewName("index");
        registry.addViewController("login/vk").setViewName("redirect:"
                + authVkService.getAuthorizationUrl());


    }
}