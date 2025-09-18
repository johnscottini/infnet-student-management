package com.example.auth.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotBlank(message = "O username é obrigatório.")
        String username,
        @NotBlank(message = "A senha é obrigatória.")
        String password,
        @NotNull(message = "O papel é obrigatório. (USER ou ADMIN)")
        UserRole role
) {
}
