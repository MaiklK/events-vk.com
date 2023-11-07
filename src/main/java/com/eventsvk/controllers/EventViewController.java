package com.eventsvk.controllers;

import com.eventsvk.entity.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventViewController {

    @GetMapping
    public String getEventsByQuery(@RequestParam(defaultValue = "") String query,
                                   @RequestParam(defaultValue = "1") String cityId,
                                   @RequestParam(defaultValue = "1") String countryId, ModelMap modelMap) {

        List<Event> events = new ArrayList<>();
        modelMap.addAttribute("events", events);
        return "event";
    }

}
