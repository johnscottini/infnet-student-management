package com.example.auth.repositories;

import com.example.auth.domain.enrollment.Enrollment;
import com.example.auth.domain.student.Student;
import com.example.auth.domain.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    List<Enrollment> findBySubject(Subject subject);
    List<Enrollment> findByStudent(Student student);
}