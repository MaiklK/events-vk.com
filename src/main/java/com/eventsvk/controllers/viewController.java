package com.eventsvk.controllers;

import com.eventsvk.entity.user.User;
import com.eventsvk.security.CustomAuthentication;
import com.eventsvk.services.AuthVkService;
import com.eventsvk.services.RoleService;
import com.eventsvk.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class viewController {
    private final AuthVkService authVkService;
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/index")
    public String getAuth(@RequestParam String code, HttpSession session) {
        CustomAuthentication customAuthentication = authVkService.getCustomAuthentication(code);
        if (customAuthentication != null) {
            SecurityContextHolder.getContext().setAuthentication(customAuthentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            for (GrantedAuthority auth: customAuthentication.getAuthorities()){
                if (auth.getAuthority().contains("ADMIN")) {
                    return "redirect:admin";
                }
            }
            return "index";
        }
        return "login";
    }

    @GetMapping("/admin")
    public String getAdminPage(@ModelAttribute ModelMap model, Principal principal) {
        model.addAttribute("newUser", new User());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        if (principal != null) {
            User user = userService.findUserByVkid(principal.getName());
            System.out.println(user);
            System.out.println(user.getVkid());
            model.addAttribute("authUser", user);
        }
        return "admin";
    }
}
