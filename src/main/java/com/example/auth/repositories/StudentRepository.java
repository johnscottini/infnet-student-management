package com.example.auth.repositories;

import com.example.auth.domain.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
