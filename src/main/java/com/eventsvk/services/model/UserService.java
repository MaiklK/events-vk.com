package com.eventsvk.services.model;

import com.eventsvk.entity.user.UserEntity;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Optional<UserEntity> findById(Long userVkId);
    void saveUser(UserEntity userEntity);
    GetResponse getUserInfo(Long userVkId, String accessToken);
    /**
     * Возвращает страницу пользователей, отсортированных по id по возрастанию.
     *
     * @param page номер страницы (0-based)
     * @param size размер страницы (количество элементов на странице)
     * @return страница пользователей
     */
    Page<UserEntity> findAllSortedById(int page, int size);
}
