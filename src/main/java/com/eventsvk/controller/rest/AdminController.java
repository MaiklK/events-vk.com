package com.eventsvk.controller.rest;

import com.eventsvk.dto.user.UserDto;
import com.eventsvk.dto.user.UserFullDto;
import com.eventsvk.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("users/{userid}")
    public UserFullDto getUserFull(@PathVariable Long userid) {
        return adminService.getFullUserById(userid);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers(@RequestParam int page,
                                     @RequestParam int size) {
        return adminService.getAllUsers(page, size);
    }

    @PatchMapping("/users/{userId}/ban")
    public void blockUser(@PathVariable Long userId) {
        adminService.banUser(userId);
    }

    @PatchMapping("/users/{userId}/unban")
    public void unblockUser(@PathVariable Long userId) {
        adminService.unbanUser(userId);
    }
}
