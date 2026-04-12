package com.eventsvk.services.model;

import java.util.List;

public interface RequestService {
    List<String> getRequests(int page);
    void addRequest(List<String> requests, String userId);
}