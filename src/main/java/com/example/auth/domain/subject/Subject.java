package com.example.auth.domain.subject;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "subject")
@Entity(name = "subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String code;

    public Subject(SubjectRequestDTO data) {
        this.name = data.name();
        this.code = data.code();
    }
}