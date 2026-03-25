package com.eventsvk.services.model;

import com.eventsvk.entity.user.RoleEntity;

public interface RoleService {
    RoleEntity getRoleByName(String roleName);
}
