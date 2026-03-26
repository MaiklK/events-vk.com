package com.eventsvk.security;

import com.eventsvk.entity.AccessTokenEntity;
import com.eventsvk.entity.user.RoleEntity;
import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.mapper.UserMapper;
import com.eventsvk.services.AdminService;
import com.eventsvk.services.VkPostAuthService;
import com.eventsvk.services.model.RoleService;
import com.eventsvk.services.model.UserService;
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
import static com.eventsvk.constant.SecurityConstant.ROLE_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkPostAuthProcessor {
    private final UserService userService;
    private final AdminService adminService;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final VkPostAuthService vkPostAuthService;

    @Async(VK_POST_OAUTH_SAVE)
    public void saveUserAfterAuthorization(OAuth2User oAuth2User, OAuth2AccessToken accessToken) {
        Long userVkId = extractUserVkId(oAuth2User);

        GetResponse vkUser = adminService.getUserInfo(userVkId, accessToken.getTokenValue());

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
        var existingUser = userService.findUserByIdOrGetFromCache(userVkId);
        if (existingUser.isEmpty()) {
            var newUser = new UserEntity();
            newUser.setRoles(Set.of(getDefaultUserRole()));
            newUser.setIsLocked(Boolean.TRUE.equals(newUser.getIsLocked()));
            return userMapper.mapFromVkUserToUserEntity(vkUser, newUser);
        }
        return userMapper.mapFromVkUserToUserEntity(vkUser, existingUser.get());
    }

    private RoleEntity getDefaultUserRole() {
        return roleService.findRoleByNameOrGetFromCache(ROLE_USER);
    }

    private AccessTokenEntity buildAccessTokenEntity(Long userVkId, String tokenValue) {
        return AccessTokenEntity.builder()
                .id(userVkId)
                .token(tokenValue)
                .isInUse(false)
                .isValid(true)
                .build();
    }
}
