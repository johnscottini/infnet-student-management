package com.example.auth.services;

import com.example.auth.domain.student.Student;
import com.example.auth.domain.student.StudentRequestDTO;
import com.example.auth.domain.student.StudentResponseDTO;
import com.example.auth.infra.exception.StudentAlreadyExistsException;
import com.example.auth.infra.exception.StudentNotFoundException;
import com.example.auth.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private UUID uuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        student = new Student();
        student.setId(uuid);
        student.setName("Jonathan");
        student.setEmail("jonathan@example.com");
        student.setCpf("12345678901");
        student.setPhoneNumber("11987654321");
        student.setAddress("Rua das Flores, 123");
    }

    @Test
    void shouldCreateStudentSuccessfully() {
        StudentRequestDTO dto = new StudentRequestDTO(
                "Jonathan Scottini",
                "12345678901",
                "jonathan@example.com",
                "11987654321",
                "Rua das Flores, 123"
        );

        when(studentRepository.existsByEmail("jonathan@example.com")).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentResponseDTO result = studentService.create(dto);

        assertNotNull(result);
        assertEquals("Jonathan Scottini", result.name());
        assertEquals("jonathan@example.com", result.email());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExistsOnCreate() {
        StudentRequestDTO dto = new StudentRequestDTO(
                "Jonathan Scottini",
                "12345678901",
                "jonathan@example.com",
                "11987654321",
                "Rua das Flores, 123"
        );

        when(studentRepository.existsByEmail("jonathan@example.com")).thenReturn(true);

        assertThrows(StudentAlreadyExistsException.class,
                () -> studentService.create(dto));

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void shouldFindStudentById() {
        when(studentRepository.findById(uuid)).thenReturn(Optional.of(student));

        StudentResponseDTO result = studentService.findById(uuid);

        assertNotNull(result);
        assertEquals("Jonathan", result.name());
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFoundById() {
        when(studentRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                () -> studentService.findById(uuid));
    }

    @Test
    void shouldReturnAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<StudentResponseDTO> result = studentService.findAll();

        assertEquals(1, result.size());
        assertEquals("Jonathan", result.get(0).name());
    }

    @Test
    void shouldUpdateStudentSuccessfully() {
        StudentRequestDTO dto = new StudentRequestDTO(
                "Jonathan Scottini",
                "12345678901",
                "jonathan@example.com",
                "11987654321",
                "Rua das Flores, 123"
        );

        when(studentRepository.findById(uuid)).thenReturn(Optional.of(student));

        StudentResponseDTO result = studentService.update(uuid, dto);

        assertNotNull(result);
        assertEquals("Jonathan Scottini", result.name());
        assertEquals("jonathan@example.com", result.email());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentStudent() {
        StudentRequestDTO dto = new StudentRequestDTO(
                "Jonathan Scottini",
                "12345678901",
                "jonathan@example.com",
                "11987654321",
                "Rua das Flores, 123"
        );

        when(studentRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                () -> studentService.update(uuid, dto));

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void shouldDeleteStudentSuccessfully() {
        when(studentRepository.existsById(uuid)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(uuid);

        studentService.delete(uuid);

        verify(studentRepository, times(1)).deleteById(uuid);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentStudent() {
        when(studentRepository.existsById(uuid)).thenReturn(false);

        assertThrows(StudentNotFoundException.class,
                () -> studentService.delete(uuid));

        verify(studentRepository, never()).deleteById(uuid);
    }
}
