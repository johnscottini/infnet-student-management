package com.example.auth.domain.student;

import java.util.UUID;

public record StudentResponseDTO(UUID id, String name, String cpf, String email, String address) {
    public StudentResponseDTO(Student student) {
        this(
                student.getId(),
                student.getName(),
                student.getCpf(),
                student.getEmail(),
                student.getAddress()
        );
    }
}
