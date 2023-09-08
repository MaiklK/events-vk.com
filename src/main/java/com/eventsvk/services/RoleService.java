package com.eventsvk.services;

import com.eventsvk.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void saveRole(Role role);

    Role getRoleById(long id);

}
