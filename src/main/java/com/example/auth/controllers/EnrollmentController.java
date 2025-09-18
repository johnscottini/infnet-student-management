package com.example.auth.controllers;

import com.example.auth.domain.enrollment.Enrollment;
import com.example.auth.domain.enrollment.EnrollmentResponseDTO;
import com.example.auth.services.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PostMapping("/enroll")
    public ResponseEntity<EnrollmentResponseDTO> enrollStudent(
            @RequestParam UUID studentId,
            @RequestParam UUID subjectId
    ) {
        Enrollment enrollment = service.enrollStudent(studentId, subjectId);
        return ResponseEntity.ok(new EnrollmentResponseDTO(enrollment));
    }

    @PutMapping("/grade/{enrollmentId}")
    public ResponseEntity<EnrollmentResponseDTO> assignGrade(
            @PathVariable UUID enrollmentId,
            @RequestParam Double grade
    ) {
        Enrollment enrollment = service.assignGrade(enrollmentId, grade);
        return ResponseEntity.ok(new EnrollmentResponseDTO(enrollment));
    }

    @GetMapping("/approved/{subjectId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> listApproved(@PathVariable UUID subjectId) {
        List<EnrollmentResponseDTO> result = service.listApproved(subjectId)
                .stream()
                .map(EnrollmentResponseDTO::new)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/failed/{subjectId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> listFailed(@PathVariable UUID subjectId) {
        List<EnrollmentResponseDTO> result = service.listFailed(subjectId)
                .stream()
                .map(EnrollmentResponseDTO::new)
                .toList();
        return ResponseEntity.ok(result);
    }
}
