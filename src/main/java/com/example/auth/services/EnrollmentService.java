package com.example.auth.services;

import com.example.auth.domain.enrollment.Enrollment;
import com.example.auth.domain.student.Student;
import com.example.auth.domain.subject.Subject;
import com.example.auth.infra.exception.StudentNotFoundException;
import com.example.auth.infra.exception.SubjectNotFoundException;
import com.example.auth.repositories.EnrollmentRepository;
import com.example.auth.repositories.StudentRepository;
import com.example.auth.repositories.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             SubjectRepository subjectRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public Enrollment enrollStudent(UUID studentId, UUID subjectId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found: " + studentId));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found: " + subjectId));

        Enrollment enrollment = new Enrollment(student, subject);
        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    public Enrollment assignGrade(UUID enrollmentId, Double grade) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found: " + enrollmentId));

        enrollment.setGrade(grade);
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> listApproved(UUID subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found: " + subjectId));

        return enrollmentRepository.findBySubject(subject)
                .stream()
                .filter(e -> e.getGrade() != null && e.getGrade() >= 7.0)
                .toList();
    }

    public List<Enrollment> listFailed(UUID subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found: " + subjectId));

        return enrollmentRepository.findBySubject(subject)
                .stream()
                .filter(e -> e.getGrade() != null && e.getGrade() < 7.0)
                .toList();
    }
}
