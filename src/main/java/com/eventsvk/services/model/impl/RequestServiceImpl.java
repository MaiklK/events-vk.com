package com.eventsvk.services.model.impl;

import com.eventsvk.entity.event.RequestEntity;
import com.eventsvk.repositories.RequestRepository;
import com.eventsvk.services.model.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private static final int DEFAULT_BATCH_SIZE = 25;
    private final RequestRepository requestRepository;

    @Override
    public List<String> getRequests(int page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_BATCH_SIZE, Sort.by(Sort.Direction.ASC, "id"));
        Page<RequestEntity> requestEntities = requestRepository.findAll(pageable);
        log.debug("Fetched page {} of requests from dic_request, size: {}, total elements: {}",
                page, requestEntities.getNumberOfElements(), requestEntities.getTotalElements());
        return requestEntities.getContent().stream()
                .map(RequestEntity::getReq)
                .toList();
    }

    @Override
    public void addRequest(List<String> requests, String userId) {
        requestRepository.saveAll(requests.stream()
                .map(name -> RequestEntity.builder()
                        .req(name)
                        .build())
                .toList());
        log.info("User with id {} added '{}' to the requests list", userId, requests);
    }
}
