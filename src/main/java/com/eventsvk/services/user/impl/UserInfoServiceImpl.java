package com.eventsvk.services.user.impl;

import com.eventsvk.services.user.UserInfoService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private static final List<Fields> fields = List.of(
            Fields.FIRST_NAME_NOM,
            Fields.LAST_NAME_NOM,
            Fields.PHOTO_BIG,
            Fields.PHOTO_ID,
            Fields.BDATE,
            Fields.SEX,
            Fields.CITY,
            Fields.COUNTERS,
            Fields.PERSONAL
    );
    private final VkApiClient vkApiClient;

    @Override
    public GetResponse getUserInfo(long vkId, String accessToken) {

        UserActor actor = new UserActor(vkId, accessToken);
        GetResponse userInfo = null;
        try {
            userInfo = vkApiClient.users()
                    .get(actor)
                    .userIds(String.valueOf(vkId))
                    .fields(fields)
                    .execute().getFirst();
        } catch (Exception ex) {
            log.error("Ошибка при получении информации о пользователе: {}", ex.getMessage());
        }

        return userInfo;
    }
}
