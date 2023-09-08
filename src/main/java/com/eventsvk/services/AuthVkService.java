package com.eventsvk.services;

import com.eventsvk.dto.UserVkDto;
import com.eventsvk.entity.Role;
import com.eventsvk.entity.User;
import com.eventsvk.security.CustomAuthentication;
import com.eventsvk.util.ConverterDto;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.OAuthException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.Fields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
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

    @Autowired
    public AuthVkService(ConverterDto converterDto, UserService userService, RoleService roleService) {
        this.converterDto = converterDto;
        this.userService = userService;
        this.roleService = roleService;
        TransportClient transportClient = new HttpTransportClient();
        this.vk = new VkApiClient(transportClient);
    }

    public String getAuthorizationUrl() {
        return URL
                + "?client_id=" + APP_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code"
                + "&scope=" + SCOPE;
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
        List<Fields> fieldsList = new ArrayList<>();
        fieldsList.add(Fields.BDATE);
        fieldsList.add(Fields.CONTACTS);
        fieldsList.add(Fields.SEX);
        fieldsList.add(Fields.CITY);
//        fieldsList.add(Fields.PERSONAL); //TODO допилисть сущности и DTO
        fieldsList.add(Fields.COUNTRY);
        fieldsList.add(Fields.COUNTERS);
        fieldsList.add(Fields.PHOTO_ID);
        return fieldsList;
    }

    public String getVkUser(String codeFlow) throws ClientException, ApiException {
        UserActor userVK = getAuth(codeFlow);
        vk.oAuth().userAuthorizationCodeFlow(APP_ID,
                CLIENT_SECRET, REDIRECT_URI, codeFlow);
        return vk.users().get(userVK).fields(getListFields()).execute().get(0).toString();
    }

    public UserVkDto getUserVkDto(String userVK) {
        return converterDto.convertToUserVkDto(userVK);
    }

    public User getUser(String codeFlow, UserVkDto userVkDto) {
        Set<Role> roles = new HashSet<>(List.of(roleService.getAllRoles().get(1)));
        return User.builder()
                .vkid(userVkDto.getId())
                .firsName(userVkDto.getFirstName())
                .lastName(userVkDto.getLastName())
                .birthdayDate(userVkDto.getBdate())
                .sex(userVkDto.getSex())
                .isClosed(userVkDto.isClosed())
                .counters(userVkDto.getCounters())
                .country(userVkDto.getCountry())
                .city(userVkDto.getCity())
                .photoId(userVkDto.getPhotoId())
                .password("1Adw*2dasHdwJ9*nHf6wHdt^")
                .codeFlow(codeFlow)
                .roles(roles)
                .build();
    }

    public CustomAuthentication getCustomAuthentication(String codeFlow) {
        User user = null;
        try {
            String vkUser = getVkUser(codeFlow);
            UserVkDto userVkDto = getUserVkDto(vkUser);
            User foundUser = userService.findUserByVkid(userVkDto.getId());
            if (foundUser == null) {
                user = getUser(codeFlow, userVkDto);
                userService.saveUser(user);
            }
            return new CustomAuthentication(user);
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
