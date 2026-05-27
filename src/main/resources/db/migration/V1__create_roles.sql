CREATE TABLE roles (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(50)  NOT NULL UNIQUE,
    permissions JSONB        NOT NULL DEFAULT '[]',
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

INSERT INTO roles (name, permissions) VALUES
    ('ADMIN',     '["*.*"]'),
    ('CAJERO',    '["sales.pos","sales.invoice.read"]'),
    ('CARNICERO', '["inventory.*","production.*"]'),
    ('AUXILIAR',  '["inventory.read","logistics.receive"]'),
    ('CONTADOR',  '["reports.*","finance.*","accounting.*"]');
