package com.eventsvk.util;

import com.eventsvk.dto.UserVkDto;
import com.eventsvk.entity.City;
import com.eventsvk.entity.Country;
import com.eventsvk.entity.Region;
import com.eventsvk.entity.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConverterDto {
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public UserVkDto convertToUserVkDto(String json) {
        try {
            return objectMapper.readValue(json, UserVkDto.class);
        } catch (Exception e) {
            e.printStackTrace();//TODO прикрутить логи
        }
        return null;
    }

    public User fromVkUserToUser(UserVkDto userVkDto) {
        return modelMapper.map(userVkDto, User.class);
    }

    public UserVkDto convertToUserDto(User user) {
        return modelMapper.map(user, UserVkDto.class);
    }

    public City fromVKCityToCity(com.vk.api.sdk.objects.database.City city) {
        return modelMapper.map(city, City.class);
    }

    public Country fromVKCountryToCountry(com.vk.api.sdk.objects.base.Country country) {
        return modelMapper.map(country, Country.class);
    }

    public Region fromVkRegionToRegion(com.vk.api.sdk.objects.database.Region region) {
        return modelMapper.map(region, Region.class);
    }
    //TODO дописать сущности, их DTO и конверторы к ним
}
