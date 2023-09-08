package com.eventsvk.configuration;

import com.eventsvk.entity.Role;
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
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);

        roleService.saveRole(adminRole);

        Role userRole = new Role("USER");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        roleService.saveRole(userRole);

//        User admin = new User("admin", "admin", (byte) 20, "a@a.ru", "admin", adminRoles);

//        userService.saveUser(admin);

//        User user = new User("user", "user", (byte) 20, "u@u.ru", "user", userRoles);
//
//        userService.saveUser(user);

//        eventService.saveEvent(new Event("EventOne", "12345678"));
//        eventService.saveEvent(new Event("EventTwo", "87654321"));
    }
}
