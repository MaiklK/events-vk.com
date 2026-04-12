package com.eventsvk.controller.rest;

import com.eventsvk.dto.EventDto;
import com.eventsvk.entity.AccessTokenEntity;
import com.eventsvk.services.model.SearchEventService;
import com.eventsvk.services.model.AccessTokenService;
import com.eventsvk.util.ExtractUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final SearchEventService searchEventService;
    private final AccessTokenService accessTokenService;

    @GetMapping
    public List<EventDto> getAllEvents(Principal principal) {
        if (principal != null) {
            Long userVkId = ExtractUtil.extractLong(principal.getName());
            Optional<AccessTokenEntity> accessToken = accessTokenService.findAccessTokenByUserId(userVkId);
            return searchEventService.searchEvent(accessToken);
        }
        return List.of();
    }
}
