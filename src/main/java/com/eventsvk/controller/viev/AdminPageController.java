package com.eventsvk.controller.viev;

import com.eventsvk.dto.user.UserDto;
import com.eventsvk.services.AdminService;
import com.eventsvk.util.ExtractUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminPageController {

    private final AdminService adminService;

    @GetMapping("/admin")
    public String getAdminPage(Principal principal, Model model) {
        if (principal != null) {
            Long userId = ExtractUtil.extractLong(principal.getName());
            UserDto authUser = adminService.getUserById(userId);
            model.addAttribute("authUser", authUser);
        }
        return "admin/admin";
    }

    @GetMapping("admin/{userId}")
    public String getUserPage(@PathVariable String userId, Model model) {
        model.addAttribute("userId", userId);
        return "admin/user";
    }
}
