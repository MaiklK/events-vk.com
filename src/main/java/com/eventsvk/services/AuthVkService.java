package com.eventsvk.services;

import com.eventsvk.dto.UserVkDto;
import com.eventsvk.entity.user.Role;
import com.eventsvk.entity.user.User;
import com.eventsvk.security.CustomAuthentication;
import com.eventsvk.util.ConverterDto;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.OAuthException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.Fields;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthVkService {
    private final VkApiClient vk;
    @Value("${client-id}")
    private int APP_ID;
    @Value("${client-secret}")
    private String CLIENT_SECRET;
    @Value("${authorization-uri}")
    private String URL;
    @Value("${redirect-uri}")
    private String REDIRECT_URI;
    @Value("${scope}")
    private String SCOPE;
    private final ConverterDto converterDto;
    private final UserService userService;
    private final RoleService roleService;


    public String getAuthorizationUrl() {
        return UriComponentsBuilder.fromUriString(URL)
                .queryParam("client_id", APP_ID)
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("response_type", "code")
                .queryParam("scope", SCOPE)
                .build()
                .toUriString();
    }

    public UserActor getAuth(String code) {
        UserAuthResponse authResponse = null;
        try {
            authResponse = vk.oAuth()
                    .userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code)
                    .execute();
        } catch (OAuthException e) {
            e.getRedirectUri();
        } catch (ClientException | ApiException e) {
            throw new RuntimeException(e);
        }
        assert authResponse != null;
        return new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
    }

    public List<Fields> getListFields() {
        return List.of(
                Fields.BDATE,
                Fields.CONTACTS,
                Fields.SEX,
                Fields.CITY,
                Fields.COUNTRY,
                Fields.COUNTERS,
                Fields.PHOTO_ID
//                Fields.PERSONAL //TODO допилисть сущности и DTO
        );
    }

    public String getVkUser(String codeFlow) throws ClientException, ApiException {
        UserActor userVK = getAuth(codeFlow);
        String user = vk.users().get(userVK).fields(getListFields()).execute().get(0).toString();
        return user;
    }

    public UserVkDto getUserVkDto(String userVK) {
        return converterDto.convertToUserVkDto(userVK);
    }

    public User getUser(String codeFlow, UserVkDto userVkDto) {
        Set<Role> roles = new HashSet<>(List.of(roleService.getRoleById(1)));
        User user = converterDto.fromVkUserToUser(userVkDto);
        user.setPassword("pOdk*efjv^21Pdlw90!fB");
        user.setRoles(roles);
        user.setCodeFlow(codeFlow);
        return user;
    }

    public CustomAuthentication getCustomAuthentication(String codeFlow) {
        try {
            String vkUser = getVkUser(codeFlow);
            UserVkDto userVkDto = getUserVkDto(vkUser);
            User foundUser = userService.findUserByVkid(userVkDto.getVkid());
            User user = foundUser != null ? foundUser : getUser(codeFlow, userVkDto);
            userService.saveUser(user);
            return new CustomAuthentication(user);
        } catch (ClientException | ApiException e) {
            e.printStackTrace();//TODO сделать логирование
        }
        return null;
    }
}
