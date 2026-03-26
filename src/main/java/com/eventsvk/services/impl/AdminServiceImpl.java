package com.eventsvk.services.impl;

import com.eventsvk.dto.user.UserDto;
import com.eventsvk.dto.user.UserFullDto;
import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.exception.GetUserInfoFromVKException;
import com.eventsvk.mapper.UserMapper;
import com.eventsvk.services.AdminService;
import com.eventsvk.services.model.CityService;
import com.eventsvk.services.model.UserService;
import com.eventsvk.util.ExtractUtil;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
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

    private final UserService userService;
    private final UserMapper userMapper;
    private final CityService cityService;
    private final VkApiClient vkApiClient;

    @Override
    public void banUser(Long userId) {
        UserEntity user = userService.findUserByIdOrGetFromCache(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с id=" + userId + " не найден"));

        if (user.getIsLocked()) {
            log.debug("Пользователь с id={} уже заблокирован", userId);
            return;
        }

        user.setIsLocked(true);
        userService.saveUser(user);
        log.info("Пользователь с id={} заблокирован (isLocked=true)", userId);
    }

    @Override
    public void unbanUser(Long userId) {
        UserEntity user = userService.findUserByIdOrGetFromCache(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с id=" + userId + " не найден"));

        if (!user.getIsLocked()) {
            log.debug("Пользователь с id={} уже разблокирован", userId);
            return;
        }

        user.setIsLocked(false);
        userService.saveUser(user);
        log.info("Пользователь с id={} разблокирован (isLocked=false)", userId);
    }

    @Override
    public UserDto getUserById(Long userId) {
        return userMapper.mapFromUserEntityToUserDto(userService.findUserByIdOrGetFromCache(userId).orElseThrow(()
                -> new IllegalArgumentException("Пользователь с id=" + userId + " не найден")));
    }

    @Override
    public UserFullDto getFullUserById(Long userId) {
        UserEntity userEntity = userService.findUserByIdOrGetFromCache(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с id=" + userId + " не найден"));

        UserFullDto userFullDto = userMapper.mapFromUserEntityToUserFullDto(userEntity);

        if (userEntity.getCityId() != null) {
            cityService.findCityByIdOrGetFromCache(userEntity.getCityId())
                    .ifPresent(city -> userFullDto.setCity(city.getTitle()));
        }

        return userFullDto;
    }

    @Override
    public List<UserDto> getAllUsers(int page, int size) {
        Page<UserEntity> usersPage = userService.findAllSortedById(page, size);
        return usersPage.getContent().stream()
                .map(userMapper::mapFromUserEntityToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public GetResponse getUserInfo(Long userVkId, String accessToken) {
        try {
            UserActor actor = new UserActor(userVkId, accessToken);
            GetResponse userInfo = vkApiClient.users()
                    .get(actor)
                    .userIds(String.valueOf(userVkId))
                    .fields(fields)
                    .execute()
                    .getFirst();

            processCityIfPresent(userInfo);

            return userInfo;
        } catch (Exception ex) {
            log.error(ERROR_GET_USER_INFO + "{}", ex.getMessage());
            throw new GetUserInfoFromVKException(ERROR_GET_USER_INFO + ex);
        }
    }

    private void processCityIfPresent(GetResponse userInfo) {
        Optional.ofNullable(userInfo.getCity())
                .ifPresent(city -> {
                    Long cityId = ExtractUtil.extractLong(city.getId());
                    String title = city.getTitle();
                    cityService.upsertCity(cityId, title);
                });
    }
}
