package com.eventsvk.services.model.impl;

import com.eventsvk.entity.event.WhiteListEntity;
import com.eventsvk.repositories.WhiteRepository;
import com.eventsvk.services.model.WhiteListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WhiteListServiceImpl implements WhiteListService {
    private final WhiteRepository whiteRepository;

    @Override
    public List<String> getWhiteList() {
        return whiteRepository.findAll()
                .stream()
                .map(WhiteListEntity::getName)
                .toList();
    }

    @Override
    public void addToWhiteList(List<String> white, String userId) {
        whiteRepository.saveAll(white.stream()
                .map(name -> WhiteListEntity.builder()
                        .name(name)
                        .build()).toList());

        log.info("User with id {} added '{}' to the white list", userId, white);
    }
}
