package com.example.auth.repositories;

import com.example.auth.domain.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {
    boolean existsByCode(String code);
}
