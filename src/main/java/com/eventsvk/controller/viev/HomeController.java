package com.eventsvk.controller.viev;

import com.eventsvk.services.model.UserService;
import com.eventsvk.util.ExtractUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    @GetMapping("/")
    public String getIndexPage(Principal principal, Model model) {
        Optional.ofNullable(principal)
                .map(Principal::getName)
                .map(ExtractUtil::extractLong)
                .flatMap(userService::findUserByIdOrGetFromCache)
                .ifPresent(user -> {
                    model.addAttribute("firstName", user.getFirstName());
                    model.addAttribute("lastName", user.getLastName());
                    model.addAttribute("roles", user.getRoles());
                });
        return "index";
    }
}
