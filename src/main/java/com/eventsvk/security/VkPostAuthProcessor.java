package com.eventsvk.security;

import com.eventsvk.entity.CityEntity;
import com.eventsvk.entity.user.AccessTokenEntity;
import com.eventsvk.entity.user.RoleEntity;
import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.mapper.VkUserMapper;
import com.eventsvk.repositories.AccessTokenRepository;
import com.eventsvk.repositories.CityRepository;
import com.eventsvk.repositories.RoleRepository;
import com.eventsvk.services.user.UserInfoService;
import com.eventsvk.services.user.UserService;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkPostAuthProcessor {

    private final UserService userService;
    private final VkUserMapper vkUserMapper;
    private final UserInfoService userInfoService;
    private final CityRepository cityRepository;
    private final RoleRepository roleRepository;
    private final AccessTokenRepository accessTokenRepository;

    @Transactional
    public void saveUserAndToken(OAuth2User oAuth2User, OAuth2AccessToken accessToken) {

        Long vkId = Optional.ofNullable(oAuth2User.getAttribute("id"))
                .map(Object::toString)
                .map(Long::valueOf)
                .orElseThrow(() ->
                        new IllegalStateException("При авторизации пользователя произошла ошибка. Отсутствует атрибут id"));

        GetResponse vkUser = userInfoService.getUserInfo(vkId, accessToken.getTokenValue());

        UserEntity userEntity = vkUserMapper.mapToUserEntity(vkUser);

        List<RoleEntity> role = roleRepository.findByName("USER_ROLE");

        if (!role.isEmpty()) {
            userEntity.setRoles(Set.copyOf(role));
        }

        userService.saveUser(userEntity);

        AccessTokenEntity tokenEntity = AccessTokenEntity.builder()
                .id(vkId)
                .token(accessToken.getTokenValue())
                .isInUse(false)
                .isValid(true)
                .build();

        accessTokenRepository.save(tokenEntity);

        Optional<CityEntity> city = Optional.ofNullable(vkUser)
                .map(UserFull::getCity)
                .map(cityObj -> CityEntity.builder()
                        .id(cityObj.getId())
                        .title(cityObj.getTitle())
                        .build());

        city.ifPresent(cityRepository::save);
    }
}
