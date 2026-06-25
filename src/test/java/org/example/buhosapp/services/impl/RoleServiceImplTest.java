package org.example.buhosapp.services.impl;

import org.example.buhosapp.common.mappers.RoleMapper;
import org.example.buhosapp.domain.dtos.request.role.CreateRoleRequest;
import org.example.buhosapp.domain.dtos.response.role.RoleResponse;
import org.example.buhosapp.domain.entities.Role;
import org.example.buhosapp.exceptions.ResourceNotFoundException;
import org.example.buhosapp.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    private UUID roleId;
    private CreateRoleRequest createRoleRequest;
    private Role roleEntity;
    private RoleResponse roleResponse;

    @BeforeEach
    void setUp() {
        roleId = UUID.randomUUID();

        createRoleRequest = CreateRoleRequest.builder()
                .name("admin")
                .description("Administrator role with full access")
                .build();

        roleEntity = Role.builder()
                .id(roleId)
                .name("admin")
                .description("Administrator role with full access")
                .build();

        roleResponse = RoleResponse.builder()
                .id(roleId)
                .build();
    }

    @Test
    void createRole_shouldPersistRole_whenDataIsValid() {
        when(roleMapper.toEntityCreate(createRoleRequest)).thenReturn(roleEntity);

        roleService.createRole(createRoleRequest);

        // Verificamos que el flujo de creación realmente delega en el repositorio
        verify(roleRepository, times(1)).save(roleEntity);
    }

    @Test
    void getRoleByName_shouldReturnRole_whenRoleExists() {
        when(roleRepository.findByName("admin")).thenReturn(Optional.of(roleEntity));
        when(roleMapper.toDto(roleEntity)).thenReturn(roleResponse);

        RoleResponse result = roleService.getRoleByName("admin");

        assertThat(result).isEqualTo(roleResponse);
        verify(roleRepository, times(1)).findByName("admin");
    }

    @Test
    void getRoleByName_shouldThrowException_whenRoleDoesNotExist() {
        when(roleRepository.findByName("ghost_role")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> roleService.getRoleByName("ghost_role"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Role Not Found");

        verify(roleRepository, times(1)).findByName("ghost_role");
    }
}