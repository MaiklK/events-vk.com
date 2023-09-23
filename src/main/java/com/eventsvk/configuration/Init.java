package com.eventsvk.configuration;

import com.eventsvk.entity.user.Role;
import com.eventsvk.repositories.CityRepository;
import com.eventsvk.services.CountryService;
import com.eventsvk.services.RegionService;
import com.eventsvk.services.User.RoleService;
import com.eventsvk.services.User.UserService;
import com.eventsvk.services.VkontakteService;
import com.vk.api.sdk.client.actors.UserActor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class Init {

    private final RoleService roleService;
    private final UserService userService;
    private final VkontakteService vkontakteService;
    private final CountryService countryService;
    private final CityRepository cityRepository;
    private final RegionService regionService;
    @Value("${acces_token}")
    private String ACCESS_TOKEN;
    @Value("${countries_code}")
    private String COUNTRIES_CODE;

    public void initRolesAndUsers() {

        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");

        roleService.saveRole(userRole);
        roleService.saveRole(adminRole);

//        User admin = User.builder()
//                .vkid("420214979")
//                .password("admin")
//                .roles(Set.of(adminRole))
//                .city(new City(1, "Москва"))
//                .country(new Country(1, "Россия"))
//                .userPersonal(new UserPersonal())
//                .counters(new UserCounters())
//                .build();
//
//        userService.saveUser(admin);
    }

    public void fillCountryDB() {
        long beginTimestamp = System.currentTimeMillis();

        countryService.saveAllCountry(vkontakteService.getAllCountriesFromVkDb(COUNTRIES_CODE));

        long endTimestamp = System.currentTimeMillis();
        System.out.printf("Total time work 'fillCountryDB' in millisecond: %d\n",
                endTimestamp - beginTimestamp);
    }

    public void fillRegionBD(int countryId) {
        regionService.saveAll(vkontakteService.getAllRegion(countryId));
    }

    public void fillCityDB(int countryId) {
        long beginTimestamp = System.currentTimeMillis();

        cityRepository.saveAll(vkontakteService.getAllCityFromDBVK(countryId));

        long endTimestamp = System.currentTimeMillis();
        System.out.printf("Total time work 'fillCityDB' in millisecond: %d, for countryId: %d\n"
                , endTimestamp - beginTimestamp, countryId);
    }

    public void getEventByQuery(String query) {
        vkontakteService.getEventsByQuery(query);
    }

    /*Метод fillCityDB заполняет базу всеми городами из БД Вконтакте, примерно 1_200_000 строк,
    ограничение вконтакте 20_000 в сутки,
    работает только если заполнена БД стран
    * */
    @PostConstruct
    public void initDB() {
        UserActor userActor = new UserActor(0, ACCESS_TOKEN);
        vkontakteService.setUserActor(userActor);
        initRolesAndUsers();
        getEventByQuery("ааа");
//        fillCountryDB();
//        for (int countryId : countryService.getAllCountryId()) {
//            fillRegionBD(countryId);
//        }
//        for (int countryId : countryService.getAllCountryId()) {
//            fillCityDB(countryId);
//        }
    }
}
