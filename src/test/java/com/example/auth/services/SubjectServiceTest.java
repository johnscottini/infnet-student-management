package com.example.auth.services;

import com.example.auth.domain.subject.Subject;
import com.example.auth.domain.subject.SubjectRequestDTO;
import com.example.auth.domain.subject.SubjectResponseDTO;
import com.example.auth.infra.exception.SubjectAlreadyExistsException;
import com.example.auth.infra.exception.SubjectNotFoundException;
import com.example.auth.repositories.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    private Subject subject;
    private UUID uuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        subject = new Subject();
        subject.setId(uuid);
        subject.setCode("2");
        subject.setName("Java");
    }

    @Test
    void shouldCreateSubjectSuccessfully() {
        SubjectRequestDTO dto = new SubjectRequestDTO("Java", "JAVA101");

        when(subjectRepository.existsByCode("JAVA101")).thenReturn(false);
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        SubjectResponseDTO result = subjectService.create(dto);

        assertNotNull(result);
        assertEquals("Java", result.name());
        assertEquals("JAVA101", result.code());
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    void shouldSaveSubject() {
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        Subject result = subjectRepository.save(subject);

        assertNotNull(result);
        assertEquals("Java", result.getName());
        verify(subjectRepository, times(1)).save(subject);
    }

    @Test
    void shouldFindSubjectById() {
        when(subjectRepository.findById(uuid)).thenReturn(Optional.of(subject));

        SubjectResponseDTO result = subjectService.findById(uuid);

        assertTrue(Objects.nonNull(result));
        assertEquals("Java", result.name());
    }

    @Test
    void shouldReturnAllSubjects() {
        when(subjectRepository.findAll()).thenReturn(List.of(subject));

        List<SubjectResponseDTO> result = subjectService.findAll();

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).name());
    }

    @Test
    void shouldUpdateSubjectSuccessfully() {
        SubjectRequestDTO dto = new SubjectRequestDTO("Advanced Java", "JAVA102");

        when(subjectRepository.findById(uuid)).thenReturn(Optional.of(subject));
        when(subjectRepository.existsByCode("JAVA102")).thenReturn(false);
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        SubjectResponseDTO result = subjectService.update(uuid, dto);

        assertNotNull(result);
        assertEquals("Advanced Java", result.name());
        assertEquals("JAVA102", result.code());
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentSubject() {
        SubjectRequestDTO dto = new SubjectRequestDTO("Advanced Java", "JAVA102");

        when(subjectRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(SubjectNotFoundException.class,
                () -> subjectService.update(uuid, dto));

        verify(subjectRepository, never()).save(any(Subject.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithExistingCode() {
        SubjectRequestDTO dto = new SubjectRequestDTO("Advanced Java", "JAVA102");

        when(subjectRepository.findById(uuid)).thenReturn(Optional.of(subject));
        when(subjectRepository.existsByCode("JAVA102")).thenReturn(true);

        assertThrows(SubjectAlreadyExistsException.class,
                () -> subjectService.update(uuid, dto));

        verify(subjectRepository, never()).save(any(Subject.class));
    }


    @Test
    void shouldDeleteSubject() {

        when(subjectRepository.existsById(uuid)).thenReturn(true);
        doNothing().when(subjectRepository).deleteById(uuid);

        subjectService.delete(uuid);

        verify(subjectRepository, times(1)).deleteById(uuid);
    }
}
