package com.eventsvk.controllers;

import com.eventsvk.entity.user.User;
import com.eventsvk.security.CustomAuthentication;
import com.eventsvk.services.User.RoleService;
import com.eventsvk.services.User.UserService;
import com.eventsvk.services.VkontakteService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class viewController {
    private final VkontakteService vkontakteService;
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("login/vk")
    public String redirectLoginVK() {
        return "redirect:"
                + vkontakteService.getAuthorizationUrl();
    }

    @GetMapping("/index")
    public String getIndex(ModelMap model, Principal principal) {
        model.addAttribute("user",
                userService.findUserByVkid(principal.getName()));
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String getAdminPage(ModelMap model, Principal principal) {
        model.addAttribute("authUser", userService.findUserByVkid(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

    @GetMapping("/check_auth")
    public String getAuth(@RequestParam String code, HttpSession session) {
        CustomAuthentication customAuthentication = vkontakteService.getCustomAuthentication(code);
        if (customAuthentication != null) {
            SecurityContextHolder.getContext().setAuthentication(customAuthentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            for (GrantedAuthority auth : customAuthentication.getAuthorities()) {
                if (auth.getAuthority().contains("ADMIN")) {
                    return "redirect:/admin";
                }
            }
            return "redirect:/index";
        }
        return "login";
    }

    @PutMapping("/user/update")
    public String updateUser(@RequestBody User user) {
        System.out.println(user);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PutMapping("/user/{id}")
    public String banUser(@PathVariable("id") long userId) {
        userService.banUser(userId);
        return "redirect:/admin";
    }
}
