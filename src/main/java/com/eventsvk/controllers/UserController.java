package com.eventsvk.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/user")
    public Map<String, String> user(Principal principal) {
        return Map.of("name", principal.getName());
    }
}
