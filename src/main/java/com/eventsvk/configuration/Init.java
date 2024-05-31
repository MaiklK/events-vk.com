package com.eventsvk.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@Slf4j
public class Init {
    
}
