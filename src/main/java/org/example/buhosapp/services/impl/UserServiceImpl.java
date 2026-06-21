package org.example.buhosapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.buhosapp.common.mappers.RoleMapper;
import org.example.buhosapp.common.mappers.UserMapper;
import org.example.buhosapp.domain.dtos.request.user.CreateUserRequest;
import org.example.buhosapp.domain.dtos.response.role.RoleResponse;
import org.example.buhosapp.domain.dtos.response.user.UserResponse;
import org.example.buhosapp.exceptions.ResourceNotFoundException;
import org.example.buhosapp.repositories.UserRepository;
import org.example.buhosapp.services.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest, String role) {
        RoleResponse roleResponse = roleService.getRoleByName(role);
        return userMapper.toDto(
                userRepository.save(
                        userMapper.toEntityCreate(createUserRequest, roleMapper.toEntity(roleResponse))
                )
        );
    }

    @Override
    public UserResponse getUserById(UUID id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found")
        ));
    }
}
