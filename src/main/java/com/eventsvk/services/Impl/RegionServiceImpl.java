package com.eventsvk.services.Impl;

import com.eventsvk.entity.Region;
import com.eventsvk.repositories.RegionRepository;
import com.eventsvk.services.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public void saveAll(List<Region> regions) {
        regionRepository.saveAll(regions);
    }

    @Override
    public List<Integer> getAllRegionId() {
        return regionRepository.findAllIds();
    }
}
