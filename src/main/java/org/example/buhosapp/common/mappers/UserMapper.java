package org.example.buhosapp.common.mappers;

import org.example.buhosapp.domain.dtos.request.user.CreateUserRequest;
import org.example.buhosapp.domain.dtos.response.user.UserResponse;
import org.example.buhosapp.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntityCreate(CreateUserRequest createUserRequest) {
        return User.builder()
                .username(createUserRequest.getUsername())
                .card(createUserRequest.getCard())
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .build();
    }

    public UserResponse toDto(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .card(user.getCard())
                .email(user.getEmail())
                .build();
    }
}
