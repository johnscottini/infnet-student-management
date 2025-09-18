package com.example.auth.domain.enrollment;

import com.example.auth.domain.student.Student;
import com.example.auth.domain.subject.Subject;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "enrollment")
@Table(name = "enrollment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private Double grade;

    public Enrollment(Student student, Subject subject) {
        this.student = student;
        this.subject = subject;
    }
}