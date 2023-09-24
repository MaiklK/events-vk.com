package com.eventsvk.controllers;

import com.eventsvk.entity.event.Event;
import com.eventsvk.services.Event.EventService;
import com.eventsvk.services.VkontakteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventViewController {
    private final VkontakteService vkontakteService;

    @GetMapping
    public String getEventsByQuery(@RequestParam(defaultValue = "") String query,
                                       @RequestParam(defaultValue = "1") String cityId,
            @RequestParam(defaultValue = "1") String countryId, ModelMap modelMap) {

        String[] args = {query, cityId, countryId};

        List<Event> events = vkontakteService.getEventsByQuery(args);
        modelMap.addAttribute("events", events);
        return "event";
    }

}
