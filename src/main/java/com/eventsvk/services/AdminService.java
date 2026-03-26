package com.eventsvk.services;

import com.eventsvk.dto.user.UserDto;
import com.eventsvk.dto.user.UserFullDto;
import com.vk.api.sdk.objects.users.responses.GetResponse;

import java.util.List;

public interface AdminService {
    void banUser(Long userId);

    void unbanUser(Long userId);

    UserDto getUserById(Long userId);

    UserFullDto getFullUserById(Long userid);

    List<UserDto> getAllUsers(int page, int size);

    GetResponse getUserInfo(Long userVkId, String accessToken);
}
