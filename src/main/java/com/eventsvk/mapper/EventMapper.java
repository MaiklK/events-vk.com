package com.eventsvk.mapper;

import com.eventsvk.dto.EventDto;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.groups.GroupIsClosed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    @Mapping(target = "isClosed", source = "isClosed")
    EventDto toEventDto(GroupFull group);

    default int map(GroupIsClosed value) {
        return Optional.ofNullable(value)
                .map(GroupIsClosed::getValue)
                .orElse(0);
    }
}
