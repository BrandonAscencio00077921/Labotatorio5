package org.example.buhosapp.services.impl;

import org.example.buhosapp.common.mappers.RoleMapper;
import org.example.buhosapp.common.mappers.UserMapper;
import org.example.buhosapp.domain.dtos.request.user.CreateUserRequest;
import org.example.buhosapp.domain.dtos.response.role.RoleResponse;
import org.example.buhosapp.domain.dtos.response.user.UserResponse;
import org.example.buhosapp.domain.entities.Role;
import org.example.buhosapp.domain.entities.User;
import org.example.buhosapp.exceptions.ResourceNotFoundException;
import org.example.buhosapp.repositories.UserRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UUID userId;
    private UUID roleId;
    private CreateUserRequest request;
    private RoleResponse roleResponse;
    private Role roleEntity;
    private User userEntity;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        roleId = UUID.randomUUID();

        request = CreateUserRequest.builder()
                .username("paco")
                .email("paco@test.com")
                .card("00000000")
                .password("123456abc")
                .build();

        roleResponse = RoleResponse.builder()
                .id(roleId)
                .build();

        roleEntity = Role.builder()
                .id(roleId)
                .name("student")
                .description("Student role, can only see reservations that have been made")
                .build();

        userEntity = User.builder()
                .id(userId)
                .username(request.getUsername())
                .email(request.getEmail())
                .card(request.getCard())
                .password(request.getPassword())
                .role(roleEntity)
                .build();

        userResponse = UserResponse.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .card(userEntity.getCard())
                .build();
    }

    @Test
    void createUser_shouldSaveUserWithCorrectRole() {
        when(roleService.getRoleByName(roleEntity.getName())).thenReturn(roleResponse);
        when(roleMapper.toEntity(roleResponse)).thenReturn(roleEntity);
        when(userMapper.toEntityCreate(request, roleEntity)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(userResponse);

        var result = userService.createUser(request, roleEntity.getName());
        assertThat(result).isEqualTo(userResponse);
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(userEntity)).thenReturn(userResponse);

        UserResponse result = userService.getUserById(userId);

        assertThat(result).isEqualTo(userResponse);
    }
}
