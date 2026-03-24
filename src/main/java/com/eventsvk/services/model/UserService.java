package com.eventsvk.services.model;

import com.eventsvk.entity.user.UserEntity;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<UserEntity> findById(Long userVkId);
    void saveUser(UserEntity userEntity);
    GetResponse getUserInfo(Long userVkId, String accessToken);
}
