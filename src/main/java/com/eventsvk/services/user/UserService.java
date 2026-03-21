package com.eventsvk.services.user;

import com.eventsvk.entity.user.RoleEntity;
import com.eventsvk.entity.user.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void saveUser(UserEntity userEntity);

    void setRoleToUser(Long vkId, RoleEntity roleEntity);

}
