package com.eventsvk.configuration;

import com.eventsvk.entity.event.Event;
import com.eventsvk.services.Event.EventService;
import com.eventsvk.services.VkontakteService;
import com.vk.api.sdk.client.actors.UserActor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class Init {

    private final VkontakteService vkontakteService;
    private final EventService eventService;
    @Value("${access_token}")
    private String ACCESS_TOKEN;

    public Collection<Event> getEventByQuery(String query, int cityId, int countryId) {

        String[] args = {query, String.valueOf(cityId), String.valueOf(countryId)};

        List<Event> eventsList = vkontakteService.getEventsByQuery(args);

//        eventService.saveAllEvents(eventsList);
        return eventsList;
    }

    public String[] getQueries() {
        return new String[]{"а", "б", "в", "г", "д", "е", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т",
                "у", "ф", "х", "ш", "щ", "ы", "э", "ю", "я", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1 ", "2", "3", "4", "5",
                "6", "7", "8", "9", "0", "", " ", "ё", "ч", "ы", "ъ"};
    }

    @PostConstruct
    public void initDB() throws InterruptedException {
        UserActor userActor = new UserActor(0, ACCESS_TOKEN);
        vkontakteService.setUserActor(userActor);
//        Set<Event> events = new HashSet<>();
//        for (String query : getQueries()){
//            events.addAll(getEventByQuery(query, 1, 1));
//            vkontakteService.pauseRequest();
//        }
//
//        eventService.saveAllEvents(events.stream().toList());
    }
}
