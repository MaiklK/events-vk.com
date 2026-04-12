package com.eventsvk.services.model;

import com.eventsvk.entity.user.UserEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findUserByIdOrGetFromCache(Long userVkId);

    void saveUser(UserEntity userEntity);

    /**
     * Возвращает страницу пользователей, отсортированных по id по возрастанию.
     *
     * @param page номер страницы (0-based)
     * @param size размер страницы (количество элементов на странице)
     * @return страница пользователей
     */
    Page<UserEntity> findAllSortedById(int page, int size);
}
