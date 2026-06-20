package org.example.buhosapp.domain.dtos.request.role;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoleRequest {
    @NotBlank(message = "role name cannot be empty")
    private String name;

    @NotBlank(message = "description cannot be empty")
    private String description;
}
