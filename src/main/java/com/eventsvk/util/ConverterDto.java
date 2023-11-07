package com.eventsvk.util;

import com.eventsvk.dto.UserVkDto;
import com.eventsvk.entity.City;
import com.eventsvk.entity.Country;
import com.eventsvk.entity.event.Event;
import com.eventsvk.entity.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConverterDto {
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public UserVkDto convertToUserVkDto(String json) {
        try {
            return objectMapper.readValue(json, UserVkDto.class);
        } catch (Exception e) {
            log.error("Не удалось конвертировать Json в объект UserVKDto {}",
                    e.getMessage());
        }
        return null;
    }

    public User fromVkUserToUser(UserVkDto userVkDto) {
        return modelMapper.map(userVkDto, User.class);
    }

    public UserVkDto convertToUserDto(User user) {
        return modelMapper.map(user, UserVkDto.class);
    }

    public City fromVKCityToCity(Object city) {
        return modelMapper.map(city, City.class);
    }

    public Country fromVKCountryToCountry(Object country) {
        return modelMapper.map(country, Country.class);
    }

    public Event fromVkGroupToEvent(Object group) {
        return modelMapper.map(group, Event.class);
    }
}
