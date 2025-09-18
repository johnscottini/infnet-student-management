package com.example.auth.services;

import com.example.auth.domain.subject.Subject;
import com.example.auth.domain.subject.SubjectRequestDTO;
import com.example.auth.domain.subject.SubjectResponseDTO;
import com.example.auth.infra.exception.SubjectAlreadyExistsException;
import com.example.auth.infra.exception.SubjectNotFoundException;
import com.example.auth.repositories.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
public class SubjectService {

    private final SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public SubjectResponseDTO create(SubjectRequestDTO dto) {
        if (repository.existsByCode(dto.code())) {
            throw new SubjectAlreadyExistsException("Subject with code " + dto.code() + " already exists.");
        }

        Subject subject = new Subject(dto);
        repository.save(subject);
        return new SubjectResponseDTO(subject);
    }

    public List<SubjectResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(SubjectResponseDTO::new)
                .toList();
    }

    public SubjectResponseDTO findById(UUID id) {
        return repository.findById(id)
                .map(SubjectResponseDTO::new)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with id: " + id));
    }

    @Transactional
    public SubjectResponseDTO update(UUID id, SubjectRequestDTO dto) {
        Subject subject = repository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with id: " + id));

        if (!subject.getCode().equals(dto.code()) && repository.existsByCode(dto.code())) {
            throw new SubjectAlreadyExistsException("Subject with code " + dto.code() + " already exists.");
        }

        subject = new Subject(
                id,
                dto.name(),
                dto.code()
        );

        repository.save(subject);
        return new SubjectResponseDTO(subject);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new SubjectNotFoundException("Subject not found with id: " + id);
        }
        repository.deleteById(id);
    }
}