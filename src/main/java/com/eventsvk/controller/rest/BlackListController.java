package com.eventsvk.controller.rest;

import com.eventsvk.services.model.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/blacklist")
@RequiredArgsConstructor
public class BlackListController {
    private final BlackListService blackListService;

    @GetMapping
    public List<String> getBlackList() {
        return blackListService.getBlackList();
    }

    @PostMapping
    public void addToBlackList(Principal principal, @RequestBody List<String> userId) {
        blackListService.addToBlackList(userId, principal.getName());
    }
}
