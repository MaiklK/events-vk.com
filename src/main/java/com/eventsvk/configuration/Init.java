package com.example.securingweb.configuration;

import com.example.securingweb.model.Role;
import com.example.securingweb.model.User;
import com.example.securingweb.services.RoleService;
import com.example.securingweb.services.UserService;
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
    public Init(UserService userService, RoleService roleService) {
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

        User admin = new User("admin", "admin", (byte) 20, "a@a.ru", "admin", adminRoles);

        userService.saveUser(admin);

        User user = new User("user", "user", (byte) 20, "u@u.ru", "user", userRoles);

        userService.saveUser(user);
    }
}
