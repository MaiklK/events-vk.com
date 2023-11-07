package com.eventsvk.services;

import com.eventsvk.entity.user.AccessToken;
import com.eventsvk.services.Event.QueryService;
import com.eventsvk.services.User.AccessTokenService;
import com.eventsvk.util.TimeUtils;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class SearchService {

    private final AccessTokenService tokenService;
    private final QueryService queryService;
    @Value("${max_attempts}")
    private int MAX_ATTEMPTS;
    @Value("${max_execute_requests}")
    private int MAX_EXECUTE_REQUESTS;

    public Set<Integer> executeSearch(String cityId) {

        List<String> executeList = getExecuteRequests(cityId);
        Set<Integer> eventsId = new CopyOnWriteArraySet<>();

        try (ExecutorService executorService = Executors.newFixedThreadPool(4)) {


            for (String s : executeList) {
                executorService.submit(new TaskExecuteRequest(tokenService, s, eventsId));
            }
            executorService.shutdown();

            executorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            // Handle the InterruptedException appropriately
            Thread.currentThread().interrupt();
            log.error("Ошибочка {}", e.getMessage());
        }
        return eventsId;
    }

    private List<String> getExecuteRequests(String cityId) {
        List<String> requestsList = queryService.getAllRequests();
        List<String> executeList = new ArrayList<>();

        IntStream.range(0, requestsList.size())
                .boxed()
                .collect(Collectors.groupingBy(index -> index / MAX_EXECUTE_REQUESTS))
                .values()
                .forEach(batchIndexes -> {
                    List<String> tempList = batchIndexes.stream()
                            .map(requestsList::get)
                            .toList();
                    String executeRequest = "var q = " + tempList.stream().collect(Collectors
                            .joining("', '", "['", "'];")) + "\n" +
                            "var ids = API.groups.search({'q':q.pop(),'type':'event','country_id':'1','city_id':'" +
                            cityId + "','future':'1','count':'1000'}).items@.id;\nwhile(q.length != 0){\n" +
                            "   ids = ids + API.groups.search({'q':q.pop(),'type':'event','country_id':'1','city_id':'" +
                            cityId + "','future':'1','count':'1000'}).items@.id;\n}\nreturn ids;";
                    executeList.add(executeRequest);
                });

        return executeList;
    }


    class TaskExecuteRequest implements Runnable {

        private final AccessTokenService tokenService;
        private final String request;
        private final VkApiClient vkClient;
        private final Set<Integer> eventsId;

        TaskExecuteRequest(AccessTokenService tokenService, String request,
                           Set<Integer> eventsId) {
            this.tokenService = tokenService;
            this.request = request;
            TransportClient transportClient = new HttpTransportClient();
            this.vkClient = new VkApiClient(transportClient);
            this.eventsId = eventsId;
        }

        @Override
        public void run() {
            AccessToken token = tokenService.getTokenNotInUse();
            tokenService.setTokenInUse(token);
            if (token != null) {
                for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
                    try {
                        UserActor userActor = new UserActor(Integer.valueOf(token.getId()), token.getToken());
                        eventsId.addAll(getEventsId(userActor, request));
                    } catch (ApiException | ClientException e) {
                        TimeUtils.pauseRequest();
                        log.error("Ошибка запроса вконтакте: {}", e.getMessage());
                    } finally {
                        tokenService.setTokenNotInUse(token);
                    }
                }
            } else {
                log.warn("Не удалось получить Access_Token");
            }
        }


        private List<Integer> getEventsId(UserActor userActor, String request) throws ClientException, ApiException {
            String result = vkClient.execute().code(userActor, request.replace("value", "2"))
                    .execute().toString().replace("[", "").replace("]", "");
            return Arrays.stream(result.split(",")).map(Integer::parseInt).toList();
        }
    }
}
