package com.eventsvk.services.model.impl;

import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.repositories.UserRepository;
import com.eventsvk.services.model.UserService;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Cache<Long, Optional<UserEntity>> userCache;

    @Override
    public Optional<UserEntity> findUserByIdOrGetFromCache(Long userVkId) {
        return userCache.get(userVkId, this::findUserById);
    }

    public Optional<UserEntity> findUserById(Long userVkId) {
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
    public Page<UserEntity> findAllSortedById(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "userVkId"));
        return userRepository.findAll(pageable);
    }
}
