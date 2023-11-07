package com.eventsvk.services.Event.Impl;

import com.eventsvk.entity.event.Request;
import com.eventsvk.repositories.QueryRepository;
import com.eventsvk.services.Event.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {
    private final QueryRepository queryRepository;

    @Transactional
    @Override
    public void saveRequest(Request request) {
        queryRepository.save(request);
    }

    @Override
    public List<String> getAllRequests() {
        return queryRepository.findAll().stream().map(Request::getReq).toList();
    }
}
