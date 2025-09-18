package com.example.auth.domain.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StudentRequestDTO(

        @NotBlank(message = "O nome é obrigatório.")
        String name,

        @NotBlank(message = "O CPF é obrigatório.")
        @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos.")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números.")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail deve estar em um formato válido.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos numéricos.")
        String phoneNumber,

        @NotBlank(message = "O endereço é obrigatório.")
        String address
) {
}
