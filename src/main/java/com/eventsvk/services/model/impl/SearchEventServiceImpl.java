package com.eventsvk.services.model.impl;

import com.eventsvk.dto.EventDto;
import com.eventsvk.entity.AccessTokenEntity;
import com.eventsvk.mapper.EventMapper;
import com.eventsvk.services.model.SearchEventService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.SearchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

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
                var cityId = 1;
                var countryId = 1;
                List<String> queries = List.of(
                        "батл", "sex", "секс", "rope", "сальса", "бачата", "урок",
                        "мастеркласс", "хастл", "танцы", "обучение", "бдсм", "бондаж",
                        "бал", "тренинг", "вальс", "танец", "танцы", ""
                );

                String code = buildExecuteCode(queries, cityId, countryId);
                String token = accessToken.get().getToken();
                Long userVkId = accessToken.get().getId();
                UserActor actor = new UserActor(userVkId, token);
                JsonElement result = vkApiClient.execute().code(actor, code).execute();

                // Преобразуем JsonElement в List<Integer>
                Set<Integer> groupIds = new HashSet<>();
                if (result.isJsonArray()) {
                    for (JsonElement element : result.getAsJsonArray()) {
                        groupIds.add(element.getAsInt());
                    }
                }
                log.info("{} groups found", groupIds.size());
                Thread.sleep(3050);
                return vkApiClient.groups()
                        .search(actor)
                        .type(SearchType.EVENT)
                        .q("01.04")
                        .cityId(1)
                        .countryId(1)
                        .count(1000)
                        .execute().getItems().stream().map(eventMapper::toEventDto).toList();
            } catch (ClientException | ApiException ex) {
                log.error("Error while searching events {}", ex.getMessage(), ex);
            } catch (Exception e) {
                log.error("some error {}", e.getMessage(), e);
            }

        }

        return List.of();
    }

    private String buildExecuteCodeChunk(List<String> queries, int cityId, int countryId) {
        StringBuilder sb = new StringBuilder();
        sb.append("return [");
        for (int i = 0; i < queries.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("API.groups.search({\"q\":\"")
                    .append(queries.get(i))
                    .append("\",\"fields\":\"site\",\"type\":\"event\",\"country_id\":\"")
                    .append(countryId)
                    .append("\",\"city_id\":\"")
                    .append(cityId)
                    .append("\",\"future\":\"1\",\"count\":\"1000\"}).items@.id");
        }
        sb.append("];");
        return sb.toString();
    }

    private String buildExecuteCode(List<String> queries, int cityId, int countryId) {
        String jsQueries = new Gson().toJson(queries);

        return """
           var cityId = %d;
           var countryId = %d;
           var queries = %s;
           var result = [];
           var i = 0;
           while (i < queries.length) {
               var groups = API.groups.search({
                   q: queries[i],
                   type: "event",
                   future: 1,
                   city_id: cityId,
                   country_id: countryId,
                   count: 1000
               });
               if (groups.items) {
                   result = result + groups.items@.id;
               }
               i = i + 1;
           }
           return result;
           """.formatted(cityId, countryId, jsQueries);
    }
}
