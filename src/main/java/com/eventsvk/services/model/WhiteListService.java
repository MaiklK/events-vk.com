package com.eventsvk.services.model;

import java.util.List;

public interface WhiteListService {
    List<String> getWhiteList();
    void addToWhiteList(List<String> white, String userId);
}
