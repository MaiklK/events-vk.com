package com.eventsvk.util;

import com.eventsvk.dto.CityDto;
import com.eventsvk.dto.CountryDto;
import com.eventsvk.dto.UserCountersDto;
import com.eventsvk.dto.UserVkDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConverterDto {
    private final ObjectMapper objectMapper;
//    private final ModelMapper modelMapper;

    public UserVkDto convertToUserVkDto(String json) {
        try {
            return objectMapper.readValue(json, UserVkDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CityDto convertToCityDto(String json) {
        try {
            return objectMapper.readValue(json, CityDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CountryDto convertCountryDto(String json) {
        try {
            return objectMapper.readValue(json, CountryDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserCountersDto convertToCounterDto(String json) {
        try {
            return objectMapper.readValue(json, UserCountersDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public User convertToUser(UserDto userDto) {
//        return modelMapper.map(userDto, User.class);
//    }
//
//    public UserDto convertToUserDto(User user) {
//        return modelMapper.map(user, UserDto.class);
//    }
    //TODO дописать сущности, их DTO и конверторы к ним
}
