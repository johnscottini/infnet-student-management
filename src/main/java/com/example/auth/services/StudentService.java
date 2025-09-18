package com.example.auth.services;

import com.example.auth.domain.student.Student;
import com.example.auth.domain.student.StudentRequestDTO;
import com.example.auth.domain.student.StudentResponseDTO;
import com.example.auth.infra.exception.StudentAlreadyExistsException;
import com.example.auth.infra.exception.StudentNotFoundException;
import com.example.auth.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public StudentResponseDTO create(StudentRequestDTO dto) {
        if (repository.existsByCpf(dto.cpf())) {
            throw new StudentAlreadyExistsException("Student with CPF " + dto.cpf() + " already exists.");
        }
        if (repository.existsByEmail(dto.email())) {
            throw new StudentAlreadyExistsException("Student with email " + dto.email() + " already exists.");
        }

        Student student = new Student(dto);
        repository.save(student);
        return new StudentResponseDTO(student);
    }

    public List<StudentResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(StudentResponseDTO::new)
                .toList();
    }

    public StudentResponseDTO findById(UUID id) {
        return repository.findById(id)
                .map(StudentResponseDTO::new)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }

    @Transactional
    public StudentResponseDTO update(UUID id, StudentRequestDTO dto) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        if (!student.getCpf().equals(dto.cpf()) && repository.existsByCpf(dto.cpf())) {
            throw new StudentAlreadyExistsException("Student with CPF " + dto.cpf() + " already exists.");
        }
        if (!student.getEmail().equals(dto.email()) && repository.existsByEmail(dto.email())) {
            throw new StudentAlreadyExistsException("Student with email " + dto.email() + " already exists.");
        }

        student = new Student(id,
                dto.name(),
                dto.cpf(),
                dto.email(),
                dto.phoneNumber(),
                dto.address());

        repository.save(student);
        return new StudentResponseDTO(student);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
