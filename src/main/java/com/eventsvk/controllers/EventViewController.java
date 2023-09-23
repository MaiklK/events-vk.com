package com.eventsvk.controllers;

import com.eventsvk.entity.event.Event;
import com.eventsvk.services.Event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventViewController {
    private final EventService eventService;

    @GetMapping
    public String getEventsByQuery(String query, ModelMap modelMap) {
        List<Event> events = eventService.getEventsFromVK(1,2, query);
        modelMap.addAttribute("events", events);
        return "event";
    }

}
