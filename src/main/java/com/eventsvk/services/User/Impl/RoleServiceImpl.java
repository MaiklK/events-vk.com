package com.eventsvk.services.User.Impl;

import com.eventsvk.entity.user.Role;
import com.eventsvk.repositories.RoleRepository;
import com.eventsvk.services.User.RoleService;
import com.eventsvk.util.exceptions.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getRoleById(long id) {
        Optional<Role> foundRole = roleRepository.findById(id);
        return foundRole.orElseThrow(() -> new RoleNotFoundException("Роль с id: " + id + " не найдена"));
    }
}
