package com.eventsvk.controller.rest;

import com.eventsvk.dto.user.UserDto;
import com.eventsvk.dto.user.UserFullDto;
import com.eventsvk.services.AdminService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
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
    public List<UserDto> getAllUsers(@RequestParam @Min(0) int page,
                                     @RequestParam @Min(0) @Max(100) int size) {
        return adminService.getAllUsers(page, size);
    }

    @PatchMapping("/users/{userId}/ban")
    public void blockUser(@PathVariable @Min(1) @Positive Long userId) {
        adminService.banUser(userId);
    }

    @PatchMapping("/users/{userId}/unban")
    public void unblockUser(@PathVariable @Min(1) @Positive Long userId) {
        adminService.unbanUser(userId);
    }
}
