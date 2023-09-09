package com.eventsvk.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class viewController {
    private final AuthVkService authVkService;
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/index")
    public String getIndex(ModelMap model) {
        model.addAttribute("user", userService.getAllUsers().get(0));
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
    public String getAuth(@RequestParam String code, HttpSession session, ModelMap model) {
        CustomAuthentication customAuthentication = authVkService.getCustomAuthentication(code);
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
}
