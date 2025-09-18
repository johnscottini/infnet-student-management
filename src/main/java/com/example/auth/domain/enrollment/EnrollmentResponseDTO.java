package com.example.auth.domain.enrollment;

import java.util.UUID;

public record EnrollmentResponseDTO(
        UUID id,
        UUID studentId,
        String studentName,
        UUID subjectId,
        String subjectName,
        Double grade
) {
    public EnrollmentResponseDTO(Enrollment e) {
        this(
                e.getId(),
                e.getStudent().getId(),
                e.getStudent().getName(),
                e.getSubject().getId(),
                e.getSubject().getName(),
                e.getGrade()
        );
    }
}
