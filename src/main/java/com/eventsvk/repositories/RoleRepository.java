package ru.maiklk.bootstrap.repositories;

import ru.maiklk.bootstrap.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
