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
    public UserEntity mapToUserEntity(UserFull from, UserEntity to) {
        if (from == null) {
            return null;
        }

        to.setUserVkId(from.getId());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setBirthdayDate(from.getBdate());
        to.setSex(mapToSex(from));
        to.setClosed(Boolean.TRUE.equals(from.getIsClosed()));
        to.setPhotoBig(resolvePhotoUrl(from));
        to.setPhotoId(from.getPhotoId());
        to.setLocked(false);
        to.setCityId(from.getId());

        to.setCounters(mapToUserCounters(from.getId(), from.getCounters()));
        to.setUserPersonal(mapToUserPersonal(from.getId(), from.getPersonal()));

        return to;
    }

    /**
     * Маппинг счетчиков пользователя
     */
    private UserCountersEntity mapToUserCounters(Long userId, UserCounters vkCounters) {
        if (vkCounters == null) {
            return null;
        }

        UserCountersEntity counters = new UserCountersEntity();
        counters.setUserVkId(userId);
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
        personal.setUserVkId(userId);
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
