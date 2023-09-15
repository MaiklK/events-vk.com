package com.eventsvk.configuration;

import com.eventsvk.services.VkontakteService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final VkontakteService vkontakteService;

    public void addViewControllers(ViewControllerRegistry registry) {
    }
}