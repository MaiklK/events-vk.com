package com.eventsvk.controllers;

import com.eventsvk.models.Event;
import com.eventsvk.models.User;
import com.eventsvk.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users/index";
    }

    @GetMapping("/new")
    public String getNewUserPage(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping()
    public String addEvent(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()){
            return "users/new";
        }
        userService.saveUser(user);
        return "redirect:users";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "users/user";
    }

    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
