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
    private final Cache<Long, UserEntity> userCache;

    @Override
    public Optional<UserEntity> findUserByIdOrGetFromCache(Long userVkId) {
        UserEntity cached = userCache.getIfPresent(userVkId);
        if (cached != null) {
            return Optional.of(cached);
        }

        Optional<UserEntity> findUser = findUserById(userVkId);

        findUser.ifPresent(user -> userCache.put(userVkId, user));
        return findUser;
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
        UserEntity saved = userRepository.save(userEntity);
        userCache.put(saved.getUserVkId(), saved);
        log.debug("Пользователь с userVkId {} успешно сохранен и кэш обновлен", saved.getUserVkId());
    }

    @Override
    public Page<UserEntity> findAllSortedById(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "userVkId"));
        return userRepository.findAll(pageable);
    }
}
