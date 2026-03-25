package com.eventsvk.services.model.impl;

import com.eventsvk.dto.UserDto;
import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.mapper.UserMapper;
import com.eventsvk.services.model.AdminService;
import com.eventsvk.services.model.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public void banUser(Long userId) {
        UserEntity user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с id=" + userId + " не найден"));

        if (user.getIsLocked()) {
            log.debug("Пользователь с id={} уже заблокирован", userId);
            return;
        }

        user.setIsLocked(true);
        userService.saveUser(user);
        log.info("Пользователь с id={} заблокирован (isLocked=true)", userId);
    }

    @Override
    public void unbanUser(Long userId) {
        UserEntity user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с id=" + userId + " не найден"));

        if (!user.getIsLocked()) {
            log.debug("Пользователь с id={} уже разблокирован", userId);
            return;
        }

        user.setIsLocked(false);
        userService.saveUser(user);
        log.info("Пользователь с id={} разблокирован (isLocked=false)", userId);
    }

    @Override
    public UserDto getUserById(Long userId) {
         return userMapper.mapFromUserEntityToUserDto(userService.findById(userId).orElseThrow(()
                 -> new IllegalArgumentException("Пользователь с id=" + userId + " не найден")));
    }

    @Override
    public List<UserDto> getAllUsers(int page, int size) {
        Page<UserEntity> usersPage = userService.findAllSortedById(page, size);
        return usersPage.getContent().stream()
                .map(userMapper::mapFromUserEntityToUserDto)
                .collect(Collectors.toList());
    }
}
