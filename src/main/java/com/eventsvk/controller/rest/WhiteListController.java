package com.eventsvk.controller.rest;

import com.eventsvk.services.model.WhiteListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/whitelist")
@RequiredArgsConstructor
public class WhiteListController {
    private final WhiteListService whiteListService;

    @GetMapping
    public List<String> getWhiteList() {
        return whiteListService.getWhiteList();
    }

    @PostMapping
    public void addToWhiteList(Principal principal, @RequestBody List<String> userId) {
        whiteListService.addToWhiteList(userId, principal.getName());
    }
}
