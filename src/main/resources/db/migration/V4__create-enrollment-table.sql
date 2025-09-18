CREATE TABLE enrollment (
    id UUID PRIMARY KEY,
    student_id UUID NOT NULL,
    subject_id UUID NOT NULL,
    grade DOUBLE PRECISION,

    CONSTRAINT fk_enrollment_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    CONSTRAINT fk_enrollment_subject FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE CASCADE,
    CONSTRAINT uq_enrollment UNIQUE (student_id, subject_id)
);
