CREATE TYPE warehouse_type_enum AS ENUM
    ('CANAL', 'CORTES', 'VISCERAS', 'EMBUTIDOS', 'DECOMISOS', 'GENERAL');

CREATE TABLE warehouses (
    id             UUID                 PRIMARY KEY DEFAULT gen_random_uuid(),
    name           VARCHAR(150)         NOT NULL,
    location       VARCHAR(255),
    warehouse_type warehouse_type_enum  NOT NULL,
    is_active      BOOLEAN              NOT NULL DEFAULT true,
    created_at     TIMESTAMPTZ          NOT NULL DEFAULT NOW()
);
