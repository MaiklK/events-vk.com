package com.eventsvk.services.model.impl;

import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.exception.GetUserInfoFromVKException;
import com.eventsvk.repositories.UserRepository;
import com.eventsvk.services.model.UserService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static String ERROR_GET_USER_INFO = "Ошибка при получении информации о пользователе: ";

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
    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> findById(Long userVkId) {
        return userRepository.findById(userVkId)
                .or(() -> {
                    log.debug("Пользователь с userVkId {} не найден", userVkId);
                    return Optional.empty();
                });
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
        log.debug("Пользователь с userVkId {} успешно сохранен", userEntity.getUserVkId());
    }

    @Override
    public GetResponse getUserInfo(Long userVkId, String accessToken) {
        UserActor actor = new UserActor(userVkId, accessToken);
        GetResponse userInfo;
        try {
            userInfo = vkApiClient.users()
                    .get(actor)
                    .userIds(String.valueOf(userVkId))
                    .fields(fields)
                    .execute().getFirst();
        } catch (Exception ex) {
            log.error(ERROR_GET_USER_INFO + "{}", ex.getMessage());
            throw new GetUserInfoFromVKException(ERROR_GET_USER_INFO + ex);
        }

        return userInfo;
    }

    @Override
    public Page<UserEntity> findAllSortedById(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "userVkId"));
        return userRepository.findAll(pageable);
    }

}
