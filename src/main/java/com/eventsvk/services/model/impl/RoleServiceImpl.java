package com.eventsvk.services.model.impl;

import com.eventsvk.entity.user.RoleEntity;
import com.eventsvk.repositories.RoleRepository;
import com.eventsvk.services.model.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ConcurrentMap<String, RoleEntity> cache = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public RoleEntity getRoleByName(String roleName) {
        return cache.computeIfAbsent(roleName, this::loadOrCreateRole);
    }

    private RoleEntity loadOrCreateRole(String roleName) {
        List<RoleEntity> roles = roleRepository.findByName(roleName);
        if (!roles.isEmpty()) {
            return roles.getFirst();
        }

        RoleEntity role = new RoleEntity();
        role.setName(roleName);
        return roleRepository.save(role);
    }

//    @Override
//    public RoleEntity getRoleByName(String roleName) {
//        List<RoleEntity> roles = roleRepository.findByName(roleName);
//        return roles.getFirst();
//    }
}
