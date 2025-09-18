CREATE TABLE student (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    phone VARCHAR(20),
    address TEXT,

    CONSTRAINT uq_student UNIQUE (cpf, email)
);