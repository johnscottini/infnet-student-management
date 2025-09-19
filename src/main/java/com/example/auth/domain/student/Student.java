package com.example.auth.domain.student;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "student")
@Entity(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "phone")
    private String phoneNumber;
    @Column
    private String address;

    public Student(StudentRequestDTO data) {
        this.name = data.name();
        this.cpf = data.cpf();
        this.email = data.email();
        this.phoneNumber = data.phoneNumber();
        this.address = data.address();
    }
}