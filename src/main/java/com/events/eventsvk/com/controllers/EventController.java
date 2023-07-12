package com.events.eventsvk.com.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.events.eventsvk.com.models.Event;
import com.events.eventsvk.com.service.EventService;

@Controller
@RequestMapping("/event")
public class EventController {

    private final EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping()
    public String getAllEvents(ModelMap model) {
        model.addAttribute("events", service.getAllEvents());
        return "event/index";
    }

    @GetMapping("/{id}")
    public String showEvent(@PathVariable long id, Model model) {
        model.addAttribute("event", service.getEvent(id));
        return "event/show";
    }

    @GetMapping("/new")
    public String getNewEventPage(@ModelAttribute("event") Event event) {
        return "event/new";
    }

    @PostMapping()
    public String addEvent(@ModelAttribute("event") @Valid Event event, BindingResult result) {
        if (result.hasErrors()){
            return "event/new";
        }
        service.addEvent(event);
        return "redirect:event";
    }

    @GetMapping("/{id}/edit")
    public String getUpdateEventPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("event", service.getEvent(id));
        return "/event/edit";
    }

    @PatchMapping("/{id}")
    public String updateEvent(@ModelAttribute("event") @Valid Event event,
                         ModelMap model, @PathVariable("id") long id,
                         BindingResult result) {
        model.addAttribute("event", event);
        if (result.hasErrors()) {
            return "event/edit";
        }
        service.update(event, id);
        return "redirect:/event";
    }

    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable("id") long id) {
        service.delete(id);
        return "redirect:/event";
    }

}
