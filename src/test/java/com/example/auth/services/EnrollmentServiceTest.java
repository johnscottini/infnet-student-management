package com.example.auth.services;

import com.example.auth.domain.enrollment.Enrollment;
import com.example.auth.domain.student.Student;
import com.example.auth.domain.subject.Subject;
import com.example.auth.infra.exception.StudentNotFoundException;
import com.example.auth.infra.exception.SubjectNotFoundException;
import com.example.auth.repositories.EnrollmentRepository;
import com.example.auth.repositories.StudentRepository;
import com.example.auth.repositories.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Student student;
    private Subject subject;
    private Enrollment enrollment;
    private UUID studentId;
    private UUID subjectId;
    private UUID enrollmentId;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();
        subjectId = UUID.randomUUID();
        enrollmentId = UUID.randomUUID();

        student = new Student();
        student.setId(studentId);
        student.setName("Jonathan");

        subject = new Subject();
        subject.setId(subjectId);
        subject.setName("Spring Boot");

        enrollment = new Enrollment(enrollmentId, student, subject, null);
    }

    @Test
    void shouldEnrollStudentSuccessfully() {
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        Enrollment result = enrollmentService.enrollStudent(studentId, subjectId);

        assertNotNull(result);
        assertEquals(student, result.getStudent());
        assertEquals(subject, result.getSubject());
        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                () -> enrollmentService.enrollStudent(studentId, subjectId));
    }

    @Test
    void shouldThrowExceptionWhenSubjectNotFound() {
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        assertThrows(SubjectNotFoundException.class,
                () -> enrollmentService.enrollStudent(studentId, subjectId));
    }

    @Test
    void shouldAssignGradeSuccessfully() {
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        Enrollment result = enrollmentService.assignGrade(enrollmentId, 8.5);

        assertEquals(8.5, result.getGrade());
        verify(enrollmentRepository, times(1)).save(enrollment);
    }

    @Test
    void shouldThrowExceptionWhenEnrollmentNotFound() {
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> enrollmentService.assignGrade(enrollmentId, 9.0));
    }

    @Test
    void shouldListApprovedStudents() {
        enrollment.setGrade(8.0);
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
        when(enrollmentRepository.findBySubject(subject)).thenReturn(List.of(enrollment));

        List<Enrollment> result = enrollmentService.listApproved(subjectId);

        assertEquals(1, result.size());
        assertEquals(student, result.get(0).getStudent());
    }

    @Test
    void shouldListFailedStudents() {
        enrollment.setGrade(5.0);
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
        when(enrollmentRepository.findBySubject(subject)).thenReturn(List.of(enrollment));

        List<Enrollment> result = enrollmentService.listFailed(subjectId);

        assertEquals(1, result.size());
        assertEquals(student, result.get(0).getStudent());
    }
}
