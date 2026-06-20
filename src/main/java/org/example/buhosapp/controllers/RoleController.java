package org.example.buhosapp.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.buhosapp.domain.dtos.request.role.CreateRoleRequest;
import org.example.buhosapp.domain.dtos.request.user.CreateUserRequest;
import org.example.buhosapp.domain.dtos.response.GeneralResponse;
import org.example.buhosapp.services.impl.RoleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/role")
@AllArgsConstructor
public class RoleController {
    private final RoleServiceImpl roleService;

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse> createRole(@RequestBody @Valid CreateRoleRequest createRoleRequest) {
        roleService.createRole(createRoleRequest);
        return buildResponse(
                "Role created successfully",
                HttpStatus.CREATED,
                null
        );
    }

    public ResponseEntity<GeneralResponse> buildResponse(String message, HttpStatus status, Object data) {
        String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();
        return ResponseEntity
                .status(status)
                .body(GeneralResponse.builder()
                        .uri(uri)
                        .message(message)
                        .status(status.value())
                        .time(LocalDateTime.now())
                        .data(data)
                        .build()
                );
    }
}
