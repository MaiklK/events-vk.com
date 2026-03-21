package com.eventsvk.services.user.impl;

import com.eventsvk.entity.user.RoleEntity;
import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.repositories.UserRepository;
import com.eventsvk.services.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
        log.info("User with vkId {} successfully saved", userEntity.getVkId());
    }

    @Override
    public void setRoleToUser(Long vkId, RoleEntity roleEntity) {
        Optional<UserEntity> remoteUser = userRepository.findById(vkId);

        if (remoteUser.isPresent()) {
            UserEntity userEntity = remoteUser.get();
            var roles = userEntity.getRoles();
            roles.add(roleEntity);
            userEntity.setRoles(roles);

            userRepository.saveAndFlush(userEntity);
        }
    }
}
