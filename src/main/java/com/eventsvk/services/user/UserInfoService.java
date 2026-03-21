package com.eventsvk.services.user;


import com.vk.api.sdk.objects.users.responses.GetResponse;

/**
 * Сервис для получения информации о пользователе
 */
public interface UserInfoService {
    GetResponse getUserInfo(long vkId, String accessToken);
}
