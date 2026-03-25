package com.eventsvk.services.model;

import com.eventsvk.dto.UserDto;

import java.util.List;

public interface AdminService {
    void banUser(Long userId);
    void unbanUser(Long userId);
    UserDto getUserById(Long userId);
    List<UserDto> getAllUsers(int page, int size);
}
