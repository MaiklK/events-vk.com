package com.eventsvk.services;

import com.eventsvk.dto.UserVkDto;
import com.eventsvk.entity.City;
import com.eventsvk.entity.Country;
import com.eventsvk.entity.Region;
import com.eventsvk.entity.event.Event;
import com.eventsvk.entity.user.Role;
import com.eventsvk.entity.user.User;
import com.eventsvk.security.CustomAuthentication;
import com.eventsvk.services.User.RoleService;
import com.eventsvk.services.User.UserService;
import com.eventsvk.util.ConverterDto;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.OAuthException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.database.responses.GetCitiesResponse;
import com.vk.api.sdk.objects.database.responses.GetCountriesResponse;
import com.vk.api.sdk.objects.database.responses.GetRegionsResponse;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.groups.GroupIsClosed;
import com.vk.api.sdk.objects.groups.SearchType;
import com.vk.api.sdk.objects.groups.responses.SearchResponse;
import com.vk.api.sdk.objects.users.Fields;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Getter
@Setter
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

    public UserAuthResponse getUserAuthResponse(String codeFlow) {
        this.codeFlow = codeFlow;
        UserAuthResponse authResponse = null;
        try {
            authResponse = vk.oAuth()
                    .userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, codeFlow)
                    .execute();
        } catch (OAuthException e) {
            e.getRedirectUri();//TODO прикрутить логирование
        } catch (ClientException e) {
            e.printStackTrace();//TODO прикрутить логирование
        } catch (ApiException e) {
            e.printStackTrace();//TODO прикрутить логирование
        }
        assert authResponse != null;
        return authResponse;
    }

    public UserActor getUserActor(UserAuthResponse userAuthVK) {
        this.userActor = new UserActor(userAuthVK.getUserId(), userAuthVK.getAccessToken());
        return this.userActor;
    }

    public List<Fields> getListFields() {
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
        return vk.users().get(this.userActor).fields(getListFields()).execute().get(0).toString();
    }

    public UserVkDto getUserVkDto(String userVK) {
        return converterDto.convertToUserVkDto(userVK);
    }

    public User getUser(UserVkDto userVkDto) {
        Set<Role> roles = new HashSet<>(List.of(roleService.getRoleById(1)));
        User user = converterDto.fromVkUserToUser(userVkDto);
        user.setPassword("pOdk*efjv^21Pdlw90!fB");
        user.setRoles(roles);
        user.setCodeFlow(this.codeFlow);
        user.setAccountNonLocked(true);
        return user;
    }

    public CustomAuthentication getCustomAuthentication(String codeFlow) {
        try {
            UserAuthResponse authResponse = getUserAuthResponse(codeFlow);
            UserActor userActor = getUserActor(authResponse);
            String vkUser = getVkUser();
            UserVkDto userVkDto = getUserVkDto(vkUser);
            User foundUser = userService.findUserByVkid(userVkDto.getVkid());
            User user = foundUser != null ? foundUser : getUser(userVkDto);
            userService.saveUser(user);
            return new CustomAuthentication(user);
        } catch (ClientException e) {
            e.printStackTrace();//TODO сделать логирование
        } catch (ApiException e) {
            e.printStackTrace();//TODO сделать логирование
        }
        return null;
    }

    public List<Country> getAllCountriesFromVkDb(String countriesCode) {
        List<Country> countries = new ArrayList<>();
        try {
            GetCountriesResponse countriesResponse = vk.database()
                    .getCountries(userActor)
                    .count(1000)
                    .needAll(true)
                    .code(countriesCode).execute();
            if (countriesResponse.getCount() != 0) {
                for (com.vk.api.sdk.objects.base.Country country : countriesResponse.getItems()) {
                    if (country.getId() > 0) {
                        countries.add(converterDto.fromVKCountryToCountry(country));
                    }
                }
            }

        } catch (ApiException e) {
            e.printStackTrace();//TODO сделать логирование
        } catch (ClientException e) {
            e.printStackTrace();//TODO сделать логирование
        }
        return countries;
    }

    public List<Region> getAllRegion(int countryId) {
        long beginTimestamp = System.currentTimeMillis();
        List<Region> regions = new ArrayList<>();
        try {
            GetRegionsResponse regionsResponse = vk.database()
                    .getRegions(userActor, countryId)
                    .count(1000)
                    .execute();
            if (regionsResponse.getCount() != 0) {
                for (com.vk.api.sdk.objects.database.Region region : regionsResponse.getItems()) {
                    if (region.getId() > 0) {
                        regions.add(converterDto.fromVkRegionToRegion(region));
                    }
                }
            }

        } catch (ApiException e) {
            e.printStackTrace();//TODO сделать логирование
        } catch (ClientException e) {
            e.printStackTrace();//TODO сделать логирование
        }
        return regions;
    }

    public List<City> getAllCityFromDBVK(int countryId) {
        long beginTimestamp = System.currentTimeMillis();
        List<City> cities = new ArrayList<>();
        try {
            GetCitiesResponse citiesResponse = vk.database().getCities(userActor,
                    countryId).needAll(true).count(1).execute();
            if (citiesResponse.getCount() != 0) {
                for (int i = 0; i < citiesResponse.getCount(); i += 1000) {
                    citiesResponse = vk.database().getCities(userActor,
                            countryId).count(1000).offset(i).needAll(true).execute();
                    for (com.vk.api.sdk.objects.database.City city : citiesResponse.getItems()) {
                        if (city.getTitle() != null) {
                            cities.add(converterDto.fromVKCityToCity(city));
                        }

                    }
                    Thread.sleep(1000);
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();//TODO сделать логирование
        } catch (ClientException e) {
            e.printStackTrace();//TODO сделать логирование
        } catch (InterruptedException e) {
            throw new RuntimeException(e);//TODO сделать логирование
        }
        long endTimestamp = System.currentTimeMillis();
        System.out.printf("Total time work 'getAllCityFromDBVK' in millisecond: %d, for country: %d\n",
                endTimestamp - beginTimestamp, countryId);
        return cities;
    }

    public List<Event> getEventsByQuery(String query) {
        List<Event> events = new ArrayList<>();
        try {
            SearchResponse groups = vk.groups().search(userActor, query)
                    .count(1000)
                    .cityId(1)
                    .countryId(1)
                    .future(true)
                    .type(SearchType.EVENT).execute();
            if (groups.getCount() > 0) {
                for (Group group: groups.getItems()) {
                    if (group.getIsClosed() == GroupIsClosed.OPEN || group.getDeactivated().isEmpty()) {
                        events.add(converterDto.fromVkGroupToEvent(group));
                    }
                }
            }

        } catch (ClientException e) {
            throw new RuntimeException(e);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        return events;
    }
}
