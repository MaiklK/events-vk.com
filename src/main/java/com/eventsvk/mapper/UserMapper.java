package com.eventsvk.mapper;

import com.eventsvk.dto.user.UserCounterDto;
import com.eventsvk.dto.user.UserDto;
import com.eventsvk.dto.user.UserFullDto;
import com.eventsvk.dto.user.UserPersonalDto;
import com.eventsvk.entity.user.UserCountersEntity;
import com.eventsvk.entity.user.UserEntity;
import com.eventsvk.entity.user.UserPersonalEntity;
import com.eventsvk.enums.*;
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

    @Mapping(target = "sex", source = "sex", qualifiedByName = "sexCodeToDescription")
    @Mapping(target = "counterDto", source = "counters", qualifiedByName = "mapToUserCounters")
    @Mapping(target = "personalDto", source = "userPersonal", qualifiedByName = "mapToUserPersonalDto")
    UserFullDto mapFromUserEntityToUserFullDto(UserEntity user);

    @Mapping(target = "sex", source = "from.sex", qualifiedByName = "mapSex")
    @Mapping(target = "photoBig", source = "from", qualifiedByName = "resolvePhotoUrl")
    @Mapping(target = "cityId", source = "from", qualifiedByName = "getCityId")
    @Mapping(target = "counters", source = "from", qualifiedByName = "mapVkUserToUserCounters")
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
    default String sexCodeToDescription(Integer sex) {
        String description = enumCodeToDescription(
                sex,
                UserSex.values(),
                UserSex::getCode,
                UserSex::getDescription
        );
        return description != null ? description : UserSex.GENDER_IS_NOT_SPECIFIED.getDescription();
    }

    @Named("mapVkUserToUserCounters")
    default UserCountersEntity mapVkUserToUserCounters(UserFull vkUser) {
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

    @Named("mapToUserCounters")
    default UserCounterDto mapToUserCounters(UserCountersEntity entity) {
        if (entity == null) {
            return null;
        }
        return UserCounterDto.builder()
                .albums(entity.getAlbums())
                .audios(entity.getAudios())
                .followers(entity.getFollowers())
                .friends(entity.getFriends())
                .gifts(entity.getGifts())
                .groups(entity.getGroups())
                .pages(entity.getPages())
                .photos(entity.getPhotos())
                .videos(entity.getVideos())
                .clipsFollowers(entity.getClipsFollowers())
                .build();
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

    @Named("mapToUserPersonalDto")
    default UserPersonalDto mapToUserPersonalDto(UserPersonalEntity entity) {
        if (entity == null) {
            return null;
        }
        return UserPersonalDto.builder()
                .inspiredBy(entity.getInspiredBy())
                .political(politicalCodeToDescription(entity.getPolitical()))
                .peopleMain(peopleMainCodeToDescription(entity.getPeopleMain()))
                .lifeMain(lifeMainCodeToDescription(entity.getLifeMain()))
                .smoking(smokingCodeToDescription(entity.getSmoking()))
                .alcohol(alcoholCodeToDescription(entity.getAlcohol()))
                .build();
    }

    @Named("politicalCodeToDescription")
    default String politicalCodeToDescription(Integer code) {
        return enumCodeToDescription(
                code,
                Political.values(),
                Political::getId,
                Political::getTitle
        );
    }

    @Named("peopleMainCodeToDescription")
    default String peopleMainCodeToDescription(Integer code) {
        return enumCodeToDescription(
                code,
                PeopleMain.values(),
                PeopleMain::getId,
                PeopleMain::getTitle
        );
    }

    @Named("lifeMainCodeToDescription")
    default String lifeMainCodeToDescription(Integer code) {
        return enumCodeToDescription(
                code,
                LifeMain.values(),
                LifeMain::getId,
                LifeMain::getTitle
        );
    }

    @Named("smokingCodeToDescription")
    default String smokingCodeToDescription(Integer code) {
        return enumCodeToDescription(
                code,
                Smoking.values(),
                Smoking::getCode,
                Smoking::getDescription
        );
    }

    @Named("alcoholCodeToDescription")
    default String alcoholCodeToDescription(Integer code) {
        return enumCodeToDescription(
                code,
                Alcohol.values(),
                Alcohol::getId,
                Alcohol::getTitle
        );
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

    private <E extends Enum<E>> String enumCodeToDescription(
            Integer code,
            E[] values,
            java.util.function.ToIntFunction<E> codeExtractor,
            java.util.function.Function<E, String> descriptionExtractor
    ) {
        if (code == null) {
            return null;
        }
        for (E value : values) {
            if (codeExtractor.applyAsInt(value) == code) {
                return descriptionExtractor.apply(value);
            }
        }
        return null;
    }
}
