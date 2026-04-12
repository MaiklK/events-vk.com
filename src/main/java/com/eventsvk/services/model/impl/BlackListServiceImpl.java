package com.eventsvk.services.model.impl;

import com.eventsvk.entity.event.BlackListEntity;
import com.eventsvk.repositories.BlackRepository;
import com.eventsvk.services.model.BlackListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlackListServiceImpl implements BlackListService {
    private final BlackRepository blackRepository;

    @Override
    public List<String> getBlackList() {
        return blackRepository.findAll()
                .stream()
                .map(BlackListEntity::getName)
                .toList();
    }

    @Override
    public void addToBlackList(List<String> black, String userId) {
        if (black.isEmpty()) {
            log.info("User with id {} tried to add empty black list", userId);
            return;
        }

        blackRepository.saveAll(black.stream()
                .map(name -> BlackListEntity.builder()
                        .name(name)
                        .build())
                .toList());

        log.info("User with id {} added {} to the black list", userId, black);
    }
}
