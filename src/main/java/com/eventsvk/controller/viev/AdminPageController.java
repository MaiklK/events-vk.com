package com.eventsvk.controller.viev;

import com.eventsvk.dto.UserDto;
import com.eventsvk.services.model.AdminService;
import com.eventsvk.util.ExtractUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminPageController {

    private final AdminService adminService;

    @GetMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        if (principal != null) {
            Long userId = ExtractUtil.extractLong(principal.getName());
            UserDto authUser = adminService.getUserById(userId);
            model.addAttribute("authUser", authUser);
        }
        return "admin";
    }
}
