package com.eventsvk.services.Event;

import com.eventsvk.entity.event.Request;

import java.util.List;

public interface QueryService {
    void saveRequest(Request request);

    List<String> getAllRequests();
}
