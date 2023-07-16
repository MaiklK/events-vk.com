package com.eventsvk.controllers;

import com.eventsvk.models.Event;
import com.eventsvk.services.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService service) {
        this.eventService = service;
    }

    @GetMapping()
    public String getAllEvents(Model model) {
//        if (bool){
//            eventService.clearEventTable();
//            eventService.restartSequenceEvent();
//            return "redirect:/events";
//        }
        model.addAttribute("events", eventService.findAllEvents());
        return "events/index";
    }

    @GetMapping("/{id}")
    public String showEvent(@PathVariable long id, Model model) {
        model.addAttribute("event", eventService.findEventById(id));
        return "events/event";
    }

    @GetMapping("/new")
    public String getNewEventPage(@ModelAttribute("event") Event event) {
        return "events/new";
    }

    @PostMapping("/new")
    public String addEvent(@ModelAttribute("event") @Valid Event event, BindingResult result) {
        if (result.hasErrors()){
            return "events/new";
        }
        eventService.saveEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/{id}/edit")
    public String getUpdateEventPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("event", eventService.findEventById(id));
        return "/events/edit";
    }

    @PatchMapping("/{id}")
    public String updateEvent(@ModelAttribute("event") @Valid Event event,
                         ModelMap model, @PathVariable("id") long id,
                         BindingResult result) {
        model.addAttribute("event", event);
        if (result.hasErrors()) {
            return "events/edit";
        }
        eventService.updateEvent(event, id);
        return "redirect:/events";
    }

    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable("id") long id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }

    @PutMapping()
    public String clearEventTable() {
        eventService.clearEventTable();
        eventService.restartSequenceEvent();
        return "redirect:/events";
    }
}
