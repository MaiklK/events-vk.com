package com.eventsvk.configuration;

import com.eventsvk.entity.user.Role;
import com.eventsvk.services.EventService;
import com.eventsvk.services.RoleService;
import com.eventsvk.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Init {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService, EventService eventService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initUsers() {

        Role adminRole = new Role("ADMIN");
        Set<Role> adminRoles = new HashSet<>(Set.of(adminRole));

        roleService.saveRole(adminRole);

        Role userRole = new Role("USER");
        Set<Role> userRoles = new HashSet<>(Set.of(userRole));

        roleService.saveRole(userRole);

//        User admin = User.builder()
//                .vkid("420214979")
//                .birthdayDate("3.4.1985")
//                .firsName("Михаил")
//                .lastName("Космачев")
//                .roles(adminRoles)
//                .password("admin")
//                .codeFlow("668d466c1cb7e1aa6a")
//                .isClosed(false)
//                .sex(1)
//                .city(new City("Москва"))
//                .country(new Country("Россия"))
//                .photoId("420214979_456239109")
//                .counters(UserCounters.builder()
//                        .clipsFollowers(10)
//                        .followers(10)
//                        .friends(20)
//                        .gifts(10)
//                        .groups(10)
//                        .pages(10)
//                        .photos(10)
//                        .subscriptions(10)
//                        .videos(10)
//                        .newPhotoTags(10)
//                        .newPhotoTags(0)
//                        .newRecognitionTags(0)
//                        .build())
//                .build();
//
//        userService.saveUser(admin);

//        User user = new User("user", "user", (byte) 20, "u@u.ru", "user", userRoles);
//
//        userService.saveUser(user);

//        eventService.saveEvent(new Event("EventOne", "12345678"));
//        eventService.saveEvent(new Event("EventTwo", "87654321"));
    }
}
