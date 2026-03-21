package com.eventsvk.mapper;

import com.eventsvk.entity.user.UserCountersEntity;
import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.entity.user.UserPersonalEntity;
import com.vk.api.sdk.objects.base.Sex;
import com.vk.api.sdk.objects.users.Personal;
import com.vk.api.sdk.objects.users.UserCounters;
import com.vk.api.sdk.objects.users.UserFull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VkUserMapper {

    /**
     * Маппинг UserFull из VK SDK на нашу сущность
     */
    public UserEntity mapToUserEntity(UserFull vkUser) {
        if (vkUser == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setVkId(vkUser.getId());
        userEntity.setFirstName(vkUser.getFirstName());
        userEntity.setLastName(vkUser.getLastName());
        userEntity.setBirthdayDate(vkUser.getBdate());
        userEntity.setSex(mapToSex(vkUser));
        userEntity.setClosed(Boolean.TRUE.equals(vkUser.getIsClosed()));
        userEntity.setPhotoBig(resolvePhotoUrl(vkUser));
        userEntity.setPhotoId(vkUser.getPhotoId());
        userEntity.setLocked(false);
        userEntity.setCityId(vkUser.getId());

        userEntity.setCounters(mapToUserCounters(vkUser.getId(), vkUser.getCounters()));
        userEntity.setUserPersonal(mapToUserPersonal(vkUser.getId(), vkUser.getPersonal()));

        return userEntity;
    }

    /**
     * Маппинг счетчиков пользователя
     */
    private UserCountersEntity mapToUserCounters(Long userId, UserCounters vkCounters) {
        if (vkCounters == null) {
            return null;
        }

        UserCountersEntity counters = new UserCountersEntity();
        counters.setUserId(userId);
        counters.setAlbums(defaultInt(vkCounters.getAlbums()));
        counters.setAudios(defaultInt(vkCounters.getAudios()));
        counters.setFollowers(defaultInt(vkCounters.getFollowers()));
        counters.setFriends(defaultInt(vkCounters.getFriends()));
        counters.setGifts(defaultInt(vkCounters.getGifts()));
        counters.setGroups(defaultInt(vkCounters.getGroups()));
        counters.setPages(defaultInt(vkCounters.getPages()));
        counters.setPhotos(defaultInt(vkCounters.getPhotos()));
        counters.setVideos(defaultInt(vkCounters.getVideos()));
        counters.setClipsFollowers(defaultLong(vkCounters.getClipsFollowers()));

        return counters;
    }

    /**
     * Маппинг личной информации
     */
    private UserPersonalEntity mapToUserPersonal(Long userId, Personal vkPersonal) {
        if (vkPersonal == null) {
            return null;
        }

        UserPersonalEntity personal = new UserPersonalEntity();
        personal.setUserId(userId);
        personal.setPolitical(defaultInt(vkPersonal.getPolitical()));
        personal.setInspiredBy(vkPersonal.getInspiredBy());
        personal.setPeopleMain(defaultInt(vkPersonal.getPeopleMain()));
        personal.setLifeMain(defaultInt(vkPersonal.getLifeMain()));
        personal.setSmoking(defaultInt(vkPersonal.getSmoking()));
        personal.setAlcohol(defaultInt(vkPersonal.getAlcohol()));

        return personal;
    }

    private Integer mapToSex(UserFull vkUser) {
        return Optional.ofNullable(vkUser)
                .map(UserFull::getSex)
                .map(Sex::getValue)
                .orElse(0);
    }

    private String resolvePhotoUrl(UserFull vkUser) {
        if (vkUser.getPhotoBig() != null) {
            return vkUser.getPhotoBig();
        }
        return vkUser.getPhoto400Orig() != null ? vkUser.getPhoto400Orig().toString() : null;
    }

    private int defaultInt(Integer value) {
        return value != null ? value : 0;
    }

    private Long defaultLong(Long value) {
        return value != null ? value : 0L;
    }
}
