CREATE TABLE subject (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    code VARCHAR(255) NOT NULL UNIQUE,

    CONSTRAINT uq_subject UNIQUE (code)
);