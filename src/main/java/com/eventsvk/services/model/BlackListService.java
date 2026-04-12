package com.eventsvk.services.model;

import java.util.List;

public interface BlackListService {
    List<String> getBlackList();
    void addToBlackList(List<String> black, String userId);
}
