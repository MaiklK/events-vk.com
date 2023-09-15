package com.eventsvk.services;

import com.eventsvk.entity.Region;

import java.util.List;

public interface RegionService {

    void saveAll(List<Region> regions);

    List<Integer> getAllRegionId();
}
