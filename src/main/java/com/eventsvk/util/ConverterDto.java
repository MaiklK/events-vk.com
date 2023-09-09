package com.eventsvk.util;

import com.eventsvk.dto.UserVkDto;
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
            e.printStackTrace();
        }
        return null;
    }

    public User fromVkUserToUser(UserVkDto userVkDto) {
        return modelMapper.map(userVkDto, User.class);
    }

    public UserVkDto convertToUserDto(User user) {
        return modelMapper.map(user, UserVkDto.class);
    }
    //TODO дописать сущности, их DTO и конверторы к ним
}
