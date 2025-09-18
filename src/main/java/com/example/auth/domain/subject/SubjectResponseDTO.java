package com.example.auth.domain.subject;

import java.util.UUID;

public record SubjectResponseDTO(UUID id, String name, String code) {
    public SubjectResponseDTO(Subject subject) {
        this(
                subject.getId(),
                subject.getName(),
                subject.getCode()
        );
    }
}
