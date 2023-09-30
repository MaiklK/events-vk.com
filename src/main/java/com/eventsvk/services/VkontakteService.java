package com.eventsvk.services;

import com.eventsvk.dto.UserVkDto;
import com.eventsvk.entity.City;
import com.eventsvk.entity.Country;
import com.eventsvk.entity.Region;
import com.eventsvk.entity.event.Event;
import com.eventsvk.entity.user.User;
import com.eventsvk.security.CustomAuthentication;
import com.eventsvk.services.User.RoleService;
import com.eventsvk.services.User.UserService;
import com.eventsvk.util.ConverterDto;
import com.eventsvk.util.function.MethodCaller;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.OAuthException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.database.responses.GetCitiesResponse;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.groups.SearchType;
import com.vk.api.sdk.objects.users.Fields;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class VkontakteService {
    private final VkApiClient vk;
    @Value("${client-id}")
    private int APP_ID;
    @Value("${client-secret}")
    private String CLIENT_SECRET;
    @Value("${authorization-uri}")
    private String URL;
    @Value("${redirect-uri}")
    private String REDIRECT_URI;
    @Value("${scope}")
    private String SCOPE;
    @Value("${pause_between_request}")
    private int PAUSE_BETWEEN_REQUEST;
    @Value("${max_attempts}")
    private int MAX_ATTEMPTS;
    private String codeFlow;
    private UserActor userActor;
    private final ConverterDto converterDto;
    private final UserService userService;
    private final RoleService roleService;
    private final CountryService countryService;


    public String getAuthorizationUrl() {
        return UriComponentsBuilder.fromUriString(URL)
                .queryParam("client_id", APP_ID)
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("response_type", "code")
                .queryParam("scope", SCOPE)
                .build()
                .toUriString();
    }

    public void getUserAuthResponse(String codeFlow) {
        this.codeFlow = codeFlow;
        UserAuthResponse authResponse;
        try {
            authResponse = vk.oAuth()
                    .userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, codeFlow)
                    .execute();
            getUserActor(authResponse);
        } catch (OAuthException e) {
            log.error("Ошибка авторизации ссылка на {}, ошибка {}", e.getRedirectUri(), e.getMessage());
        } catch (ClientException | ApiException e) {
            log.error("Произошла ошибка при попытке авторизоваться {}", e.getMessage());
        }
    }

    public void getUserActor(UserAuthResponse userAuthVK) {
        this.userActor = new UserActor(userAuthVK.getUserId(), userAuthVK.getAccessToken());
    }

    public List<Fields> getFieldsList() {
        return List.of(
                Fields.BDATE,
                Fields.CONTACTS,
                Fields.SEX,
                Fields.CITY,
                Fields.COUNTRY,
                Fields.COUNTERS,
                Fields.PHOTO_ID,
                Fields.PHOTO_BIG,
                Fields.PERSONAL
        );
    }

    public String getVkUser() throws ClientException, ApiException {
        return vk.users().get(this.userActor)
                .fields(getFieldsList())
                .execute()
                .get(0)
                .toString();
    }

    public UserVkDto getUserVkDto(String userVK) {
        return converterDto.convertToUserVkDto(userVK);
    }

    public User getUser(UserVkDto userVkDto) {
        User user = converterDto.fromVkUserToUser(userVkDto);
        user.setPassword("pOdk*efjv^21Pdlw90!fB");
        user.setRoles(Set.of(roleService.getRoleById(1)));
        user.setAccessToken(userActor.getAccessToken());
        user.setAccountNonLocked(true);
        return user;
    }

    public void pauseRequest() {
        try {
            Thread.sleep(PAUSE_BETWEEN_REQUEST);
        } catch (InterruptedException e) {
            log.error("Ошибка ожидания: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public List<?> apiVkMethod(int maxAttempts, MethodCaller methodCaller, String[] args) {
        List<?> list = new ArrayList<>();
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try {
                list = methodCaller.callMethod(args);
                return list;
            } catch (ApiException | ClientException ex) {
                pauseRequest();
                log.error("Ошибка {}", ex.getMessage());
            }
        }
        return list;
    }

    public List<com.vk.api.sdk.objects.database.City> getCitiesResponse(String[] args)
            throws ClientException, ApiException {
        List<com.vk.api.sdk.objects.database.City> cities = new ArrayList<>();
        GetCitiesResponse citiesResponse = vk.database().getCities(userActor, Integer.parseInt(args[0]))
                .needAll(true)
                .count(1)
                .execute();
        pauseRequest();
        for (int i = 0; i < citiesResponse.getCount(); i += 1000) {
            citiesResponse = vk.database().getCities(userActor, Integer.parseInt(args[0]))
                    .count(1000)
                    .offset(i)
                    .needAll(true)
                    .execute();
            cities.addAll(citiesResponse.getItems());
            pauseRequest();
        }
        return cities;
    }

    public List<Group> getSearchResponseGroups(String[] args) throws ClientException, ApiException {
        return vk.groups().search(userActor, args[0])
                .count(1000)
                .cityId(Integer.valueOf(args[1]))
                .countryId(Integer.valueOf(args[2]))
                .future(true)
                .type(SearchType.EVENT)
                .execute()
                .getItems();
    }

    public List<City> converterFromVkCity(List<?> citiesList) {
        List<City> cities = new ArrayList<>();
        if (!citiesList.isEmpty()) {
            for (Object object : citiesList) {
                cities.add(converterDto.fromVKCityToCity(object));
            }
        }
        return cities;
    }

    public List<Event> converterFromVkGroup(List<?> eventList, String[] args) {
        List<Event> events = new ArrayList<>();
        if (!eventList.isEmpty()) {
            for (Object object : eventList) {
                Event event = converterDto.fromVkGroupToEvent(object);
                event.setCityId(Integer.parseInt(args[1]));
                events.add(event);
            }
        }
        return events;
    }

    public List<City> getCitiesByCountryId(String[] args) {
        return converterFromVkCity(apiVkMethod(MAX_ATTEMPTS, this::getCitiesResponse, args));
    }

    public List<Event> getEventsByQuery(String[] args) {
        return converterFromVkGroup(apiVkMethod(MAX_ATTEMPTS, this::getSearchResponseGroups, args), args);
    }

    public CustomAuthentication getCustomAuthentication(String codeFlow) {
        try {
            getUserAuthResponse(codeFlow);
            String vkUser = getVkUser();
            UserVkDto userVkDto = getUserVkDto(vkUser);
            User foundUser = userService.findUserByVkid(userVkDto.getVkid());
            User user = foundUser != null ? foundUser : getUser(userVkDto);
            userService.saveUser(user);
            return new CustomAuthentication(user);
        } catch (ClientException | ApiException e) {
            log.error("Ошибка при попытке аутентификации: {}", e.getMessage());
        }
        return null;
    }
}
