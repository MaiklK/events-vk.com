package com.eventsvk.configuration;

import com.eventsvk.entity.event.Event;
import com.eventsvk.repositories.CityRepository;
import com.eventsvk.services.CountryService;
import com.eventsvk.services.Event.EventService;
import com.eventsvk.services.VkontakteService;
import com.vk.api.sdk.client.actors.UserActor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class Init {

    private final VkontakteService vkontakteService;
    private final CityRepository cityRepository;
    private final EventService eventService;
    private final CountryService countryService;
    @Value("${access_token}")
    private String ACCESS_TOKEN;

    public void fillCityDB(int countryId) {
        long beginTimestamp = System.currentTimeMillis();
        String[] args = {String.valueOf(countryId)};

        cityRepository.saveAll(vkontakteService.getCitiesByCountryId(args));

        long endTimestamp = System.currentTimeMillis();
        System.out.printf("Total time work 'fillCityDB' in millisecond: %d, for countryId: %d\n"
                , endTimestamp - beginTimestamp, countryId);
    }

    public void getEventByQuery(String query, int cityId, int countryId) {

        String[] args = {query, String.valueOf(cityId), String.valueOf(countryId)};

        List<Event> eventsList = vkontakteService.getEventsByQuery(args);

        eventService.saveAllEvents(eventsList);
    }

    /*Метод fillCityDB заполняет базу всеми городами из БД Вконтакте, примерно 1_200_000 строк,
    ограничение вконтакте 20_000 в сутки,
    работает только если заполнена БД стран
    * */
    @PostConstruct
    public void initDB() {
        UserActor userActor = new UserActor(0, ACCESS_TOKEN);
        vkontakteService.setUserActor(userActor);
//        getEventByQuery("ааа", 1, 1);
//        for (int countryId : countryService.getAllCountryId()) {
//            fillCityDB(countryId);
//        }
    }
}
