package com.eventsvk.services.model;

import com.eventsvk.dto.EventDto;
import com.eventsvk.entity.AccessTokenEntity;

import java.util.List;
import java.util.Optional;

public interface SearchEventService {
    List<EventDto> searchEvent(Optional<AccessTokenEntity> accessToken);
}
