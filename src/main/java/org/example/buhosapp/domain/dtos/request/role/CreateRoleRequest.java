package org.example.buhosapp.domain.dtos.request.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRoleRequest {
    @NotBlank(message = "role name cannot be empty")
    private String name;

    @NotBlank(message = "description cannot be empty")
    private String description;
}
