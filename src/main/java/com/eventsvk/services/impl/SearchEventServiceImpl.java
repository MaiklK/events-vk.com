package com.eventsvk.services.impl;

import com.eventsvk.dto.EventDto;
import com.eventsvk.entity.AccessTokenEntity;
import com.eventsvk.mapper.EventMapper;
import com.eventsvk.services.SearchEventService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.SearchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchEventServiceImpl implements SearchEventService {
    private final VkApiClient vkApiClient;
    private final EventMapper eventMapper;

    @Override
    public List<EventDto> searchEvent(Optional<AccessTokenEntity> accessToken) {
        if (accessToken.isPresent()) {
            try {
                String token = accessToken.get().getToken();
                Long userVkId = accessToken.get().getId();
                UserActor actor = new UserActor(userVkId, token);
                return vkApiClient.groups()
                        .search(actor)
                        .type(SearchType.EVENT)
                        .q("27.03")
                        .cityId(1)
                        .countryId(1)
                        .count(1000)
                        .execute().getItems().stream().map(eventMapper::toEventDto).toList();
            } catch (ClientException | ApiException ex) {
                log.error("Error while searching events {}", ex.getMessage(), ex);
            }

        }

        return List.of();
    }
}
