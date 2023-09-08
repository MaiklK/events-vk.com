package com.eventsvk.util;

import com.eventsvk.dto.UserVkDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConverterJSON {
    private final ObjectMapper objectMapper;
    public UserVkDto convertToIndividual(String json) {
        try {
            return objectMapper.readValue(json, UserVkDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
