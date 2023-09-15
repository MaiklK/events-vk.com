package com.eventsvk.services.User;

import com.eventsvk.entity.user.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void saveRole(Role role);

    Role getRoleById(long id);

}
