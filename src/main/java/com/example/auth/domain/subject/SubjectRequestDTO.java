package com.example.auth.domain.subject;

import jakarta.validation.constraints.NotBlank;

public record SubjectRequestDTO(

        @NotBlank(message = "O nome é obrigatório.")
        String name,

        @NotBlank(message = "O código é obrigatório.")
        String code
) {
}
