package com.eventsvk.security;

import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.services.model.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        long userVkId;
        try {
            userVkId = Long.parseLong(username);
        } catch (NumberFormatException ex) {
            throw new UsernameNotFoundException("Некорректный формат username (ожидался VK ID): " + username);
        }

        UserEntity userEntity = userService.findUserByIdOrGetFromCache(userVkId)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с VK ID " + userVkId + " не найден"));

        boolean enabled = userEntity.getIsLocked();

        return User.withUsername(String.valueOf(userEntity.getUserVkId()))
                .password("")
                .authorities(userEntity.getRoles())
                .accountLocked(enabled)
                .disabled(enabled)
                .build();
    }
}
