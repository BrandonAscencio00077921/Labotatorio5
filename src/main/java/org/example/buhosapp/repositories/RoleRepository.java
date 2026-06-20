package org.example.buhosapp.repositories;

import org.example.buhosapp.domain.dtos.response.role.RoleResponse;
import org.example.buhosapp.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    RoleResponse findByName(String name);
}
