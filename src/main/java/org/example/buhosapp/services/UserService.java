package org.example.buhosapp.services;

import org.example.buhosapp.domain.dtos.request.user.CreateUserRequest;
import org.example.buhosapp.domain.dtos.response.user.UserResponse;

import java.util.UUID;

public interface UserService {
    UserResponse createUser(CreateUserRequest userRequest, String role);
    UserResponse getUserById(UUID id);
}
