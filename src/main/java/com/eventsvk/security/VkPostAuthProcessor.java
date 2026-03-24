package com.eventsvk.security;

import com.eventsvk.entity.AccessTokenEntity;
import com.eventsvk.entity.user.RoleEntity;
import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.mapper.VkUserMapper;
import com.eventsvk.services.VkPostAuthService;
import com.eventsvk.services.model.CityService;
import com.eventsvk.services.model.RoleService;
import com.eventsvk.services.model.UserService;
import com.eventsvk.util.ExtractUtil;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.eventsvk.constant.AsyncExecutorName.VK_POST_OAUTH_SAVE;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkPostAuthProcessor {

    private final static String USER_ROLE = "ROLE_USER";

    private final UserService userService;
    private final VkUserMapper vkUserMapper;
    private final RoleService roleService;
    private final CityService cityService;
    private final VkPostAuthService vkPostAuthService;

    @Async(VK_POST_OAUTH_SAVE)
    public void saveUserAfterAuthorization(OAuth2User oAuth2User, OAuth2AccessToken accessToken) {
        Long userVkId = extractUserVkId(oAuth2User);

        GetResponse vkUser = userService.getUserInfo(userVkId, accessToken.getTokenValue());

        upsertCityIfPresent(vkUser);

        UserEntity userEntity = buildOrUpdateUserEntity(userVkId, vkUser);

        AccessTokenEntity tokenEntity = buildAccessTokenEntity(userVkId, accessToken.getTokenValue());

        vkPostAuthService.saveUserAndToken(userEntity, tokenEntity);
    }

    public static Long extractUserVkId(OAuth2User oAuth2User) {
        return Optional.ofNullable(oAuth2User.getAttribute("id"))
                .map(Object::toString)
                .map(Long::valueOf)
                .orElseThrow(() ->
                        new IllegalStateException("При авторизации пользователя произошла ошибка. Отсутствует атрибут id"));

    }

    private UserEntity buildOrUpdateUserEntity(Long userVkId, GetResponse vkUser) {
        var existingUser = userService.findById(userVkId);
        if (existingUser.isEmpty()) {
            var newUser = new UserEntity();
            newUser.setRoles(Set.of(getDefaultUserRole()));
            return vkUserMapper.mapToUserEntity(vkUser, newUser);
        }
        return vkUserMapper.mapToUserEntity(vkUser, existingUser.get());
    }

    private RoleEntity getDefaultUserRole() {
        return roleService.getRoleByName(USER_ROLE);
    }

    private AccessTokenEntity buildAccessTokenEntity(Long userVkId, String tokenValue) {
        return AccessTokenEntity.builder()
                .id(userVkId)
                .token(tokenValue)
                .isInUse(false)
                .isValid(true)
                .build();
    }

    private void upsertCityIfPresent(GetResponse vkUser) {
        Optional.ofNullable(vkUser)
                .map(GetResponse::getCity)
                .ifPresent(city -> {
                    Long cityId = ExtractUtil.extractLong(city.getId());
                    String title = city.getTitle();
                    cityService.upsertCity(cityId, title);
                });
    }
}
