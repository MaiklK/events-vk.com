package ru.maiklk.bootstrap.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maiklk.bootstrap.model.User;
import ru.maiklk.bootstrap.services.RoleService;
import ru.maiklk.bootstrap.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class adminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public adminController(UserService usersService, RoleService roleService) {
        this.userService = usersService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAdminPanel(Principal principal, Model model) {

        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("authUser", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        return "admin/index";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("newUser") @Valid User newUser, BindingResult bindingResult) {

        userService.saveUser(newUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/users/{id}")
    public String deleteEvent(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/users/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             @PathVariable("id") long id) {
        userService.updateUser(user, id);
        return "redirect:/admin";
    }
}
