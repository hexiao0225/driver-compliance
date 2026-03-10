INSERT INTO document_types (id, code, name)
VALUES ('aaaaaaaa-0000-0000-0000-000000000001', 'drivers_license', 'Driver''s License');

INSERT INTO drivers (id, name, email)
VALUES
    ('bbbbbbbb-0000-0000-0000-000000000001', 'Alice Johnson', 'alice@acme.com'),
    ('bbbbbbbb-0000-0000-0000-000000000002', 'Bob Martinez',  'bob@acme.com'),
    ('bbbbbbbb-0000-0000-0000-000000000003', 'Carol Lee',     'carol@acme.com'),
    ('bbbbbbbb-0000-0000-0000-000000000004', 'David Kim',     'david@acme.com');

-- Alice: valid license expiring 2029-01-01
INSERT INTO documents (driver_id, document_type_id, issued_date, expiration_date)
VALUES (
    'bbbbbbbb-0000-0000-0000-000000000001',
    'aaaaaaaa-0000-0000-0000-000000000001',
    '2024-01-01',
    '2029-01-01'
);

-- Bob: license expiring in 15 days
INSERT INTO documents (driver_id, document_type_id, issued_date, expiration_date)
VALUES (
    'bbbbbbbb-0000-0000-0000-000000000002',
    'aaaaaaaa-0000-0000-0000-000000000001',
    '2024-01-01',
    CURRENT_DATE + INTERVAL '15 days'
);

-- Carol: expired license
INSERT INTO documents (driver_id, document_type_id, issued_date, expiration_date)
VALUES (
    'bbbbbbbb-0000-0000-0000-000000000003',
    'aaaaaaaa-0000-0000-0000-000000000001',
    '2020-01-01',
    '2024-01-01'
);

-- David: no documents (intentionally omitted)
