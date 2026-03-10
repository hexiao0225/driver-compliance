CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE drivers (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        TEXT NOT NULL,
    email       TEXT,
    created_at  TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE document_types (
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code TEXT UNIQUE NOT NULL,
    name TEXT NOT NULL
);

CREATE TABLE documents (
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    driver_id        UUID NOT NULL REFERENCES drivers(id) ON DELETE CASCADE,
    document_type_id UUID NOT NULL REFERENCES document_types(id),
    issued_date      DATE NOT NULL,
    expiration_date  DATE,
    created_at       TIMESTAMPTZ DEFAULT now()
);
