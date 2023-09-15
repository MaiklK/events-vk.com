package com.eventsvk.services.Impl;

import com.eventsvk.entity.Region;
import com.eventsvk.repositories.RegionRepository;
import com.eventsvk.services.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    @Override
    public void saveAll(List<Region> regions) {
        regionRepository.saveAll(regions);
    }

    @Override
    public List<Integer> getAllRegionId() {
        return regionRepository.findAllIds();
    }
}
