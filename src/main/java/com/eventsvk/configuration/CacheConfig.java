package com.eventsvk.configuration;

import com.eventsvk.entity.CityEntity;
import com.eventsvk.entity.user.RoleEntity;
import com.eventsvk.entity.user.UserEntity;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfig {
    @Bean
    public Cache<Long, CityEntity> cityCache() {
        return Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(Duration.ofHours(24))
                .recordStats()
                .build();
    }

    @Bean
    public Cache<String, RoleEntity> roleCache() {
        return Caffeine.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(Duration.ofHours(24))
                .recordStats()
                .build();
    }

    @Bean
    public Cache<Long, UserEntity> userCache() {
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofHours(24))
                .recordStats()
                .build();
    }
}
