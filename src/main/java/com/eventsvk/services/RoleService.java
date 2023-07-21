package ru.maiklk.bootstrap.services;

import ru.maiklk.bootstrap.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void saveRole(Role role);

    Role getRoleById(long id);

}
