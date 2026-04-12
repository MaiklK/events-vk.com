package com.eventsvk.controller.rest;

import com.eventsvk.services.model.BlackListService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Validated
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
    public void addToBlackList(Principal principal, @RequestBody @NotEmpty List<String> blackList) {
        blackListService.addToBlackList(blackList, principal.getName());
    }
}
