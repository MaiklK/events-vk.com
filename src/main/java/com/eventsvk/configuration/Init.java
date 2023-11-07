package com.eventsvk.configuration;

import com.eventsvk.services.Event.EventService;
import com.eventsvk.services.Event.QueryService;
import com.eventsvk.services.SearchService;
import com.eventsvk.services.User.AccessTokenService;
import com.eventsvk.services.VkontakteService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@Slf4j
public class Init {

    private static final int KNOWN_CAPACITY = 3_500;
    private final VkontakteService vkontakteService;
    private final EventService eventService;
    private final AccessTokenService tokenService;
    @Value("${access_token}")
    private String ACCESS_TOKEN;
    private final QueryService queryService;
    private final SearchService searchService;

    @SneakyThrows
    @PostConstruct
    public void initDB() {

        Set<Integer> eventsId = new HashSet<>();
        eventsId.addAll(searchService.executeSearch("2"));
        log.info("Найдено {} уникальных событий для города с id: {}", eventsId.size(), 2);
    }
}
