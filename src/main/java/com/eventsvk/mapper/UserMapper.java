package com.eventsvk.mapper;

import com.eventsvk.dto.UserDto;
import com.eventsvk.entity.user.UserCountersEntity;
import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.entity.user.UserPersonalEntity;
import com.eventsvk.enums.UserSex;
import com.eventsvk.util.ExtractUtil;
import com.vk.api.sdk.objects.base.Sex;
import com.vk.api.sdk.objects.users.Personal;
import com.vk.api.sdk.objects.users.UserCounters;
import com.vk.api.sdk.objects.users.UserFull;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "sex", source = "user.sex", qualifiedByName = "sexCodeToDescription")
    UserDto mapFromUserEntityToUserDto(UserEntity user);

    @Mapping(target = "sex", source = "from.sex", qualifiedByName = "mapSex")
    @Mapping(target = "photoBig", source = "from", qualifiedByName = "resolvePhotoUrl")
    @Mapping(target = "cityId", source = "from", qualifiedByName = "getCityId")
    @Mapping(target = "counters", source = "from", qualifiedByName = "mapToUserCounters")
    @Mapping(target = "userPersonal", source = "from", qualifiedByName = "mapToUserPersonal")
    UserEntity mapFromVkUserToUserEntity(UserFull from, @MappingTarget UserEntity to);

    @Named("mapSex")
    default int mapSex(Sex sex) {
        if (sex == null) {
            return UserSex.GENDER_IS_NOT_SPECIFIED.getCode();
        }
        return switch (sex) {
            case FEMALE -> UserSex.FEMALE.getCode();
            case MALE -> UserSex.MALE.getCode();
            default -> UserSex.GENDER_IS_NOT_SPECIFIED.getCode();
        };
    }

    @Named("sexCodeToDescription")
    default String sexCodeToDescription(int sex) {
        for (UserSex value : UserSex.values()) {
            if (value.getCode() == sex) {
                return value.getDescription();
            }
        }
        return UserSex.GENDER_IS_NOT_SPECIFIED.getDescription();
    }

    @Named("mapToUserCounters")
    default UserCountersEntity mapToUserCounters(UserFull vkUser) {
        UserCounters vkCounters = vkUser.getCounters();
        if (vkCounters == null) {
            return null;
        }
        UserCountersEntity counters = new UserCountersEntity();
        counters.setUserVkId(vkUser.getId());
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

    @Named("mapToUserPersonal")
    default UserPersonalEntity mapToUserPersonal(UserFull vkUser) {
        Personal vkPersonal = vkUser.getPersonal();
        if (vkPersonal == null) {
            return null;
        }
        UserPersonalEntity personal = new UserPersonalEntity();
        personal.setUserVkId(vkUser.getId());
        personal.setPolitical(defaultInt(vkPersonal.getPolitical()));
        personal.setInspiredBy(vkPersonal.getInspiredBy());
        personal.setPeopleMain(defaultInt(vkPersonal.getPeopleMain()));
        personal.setLifeMain(defaultInt(vkPersonal.getLifeMain()));
        personal.setSmoking(defaultInt(vkPersonal.getSmoking()));
        personal.setAlcohol(defaultInt(vkPersonal.getAlcohol()));
        return personal;
    }

    @Named("resolvePhotoUrl")
    default String resolvePhotoUrl(UserFull vkUser) {
        if (vkUser.getPhotoBig() != null) {
            return vkUser.getPhotoBig();
        }
        return vkUser.getPhoto400Orig() != null ? vkUser.getPhoto400Orig().toString() : null;
    }

    @Named("getCityId")
    default Long getCityId(UserFull vkUser) {
        if (vkUser.getCity() == null) {
            return null;
        }
        return ExtractUtil.extractLong(vkUser.getCity().getId());
    }

    default int defaultInt(Integer value) {
        return value != null ? value : 0;
    }

    default Long defaultLong(Long value) {
        return value != null ? value : 0L;
    }
}
