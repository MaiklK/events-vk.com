package com.eventsvk.services.model.impl;

import com.eventsvk.entity.user.RoleEntity;
import com.eventsvk.repositories.RoleRepository;
import com.eventsvk.services.model.RoleService;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final Cache<String, RoleEntity> roleCache;

    @Override
    @Transactional
    public RoleEntity findRoleByNameOrGetFromCache(String roleName) {
        return roleCache.get(roleName, this::loadOrCreateRole);
    }

    private RoleEntity loadOrCreateRole(String roleName) {
        return roleRepository.findFirstByName(roleName)
                .orElseGet(() -> {
                    RoleEntity role = new RoleEntity();
                    role.setName(roleName);
                    return roleRepository.save(role);
                });
    }

//    @Override
//    public RoleEntity getRoleByName(String roleName) {
//        List<RoleEntity> roles = roleRepository.findByName(roleName);
//        return roleMapper.fromRoleEntityToRoleDto(roles.getFirst());
//    }
}
