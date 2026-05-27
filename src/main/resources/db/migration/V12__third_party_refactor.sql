-- ─────────────────────────────────────────────────────────────────
-- V12 — Refactorización completa de Terceros
-- ─────────────────────────────────────────────────────────────────

-- 1. Tipos de tercero (dinámicos, configurables por el usuario)
CREATE TABLE third_party_categories (
    id        UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    name      VARCHAR(20)  NOT NULL UNIQUE,
    base_type VARCHAR(30)  NOT NULL, -- CLIENT | SUPPLIER | EMPLOYEE | BOTH
    active    BOOLEAN      NOT NULL DEFAULT TRUE
);

INSERT INTO third_party_categories (name, base_type) VALUES
    ('Cliente',           'CLIENT'),
    ('Proveedor',         'SUPPLIER'),
    ('Empleado',          'EMPLOYEE'),
    ('Cliente/Proveedor', 'BOTH');

-- 2. Tipos de identificación
CREATE TABLE identification_types (
    id          UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    code        VARCHAR(10)  NOT NULL UNIQUE,
    name        VARCHAR(50) NOT NULL,
    requires_dv BOOLEAN      NOT NULL DEFAULT FALSE,
    active      BOOLEAN      NOT NULL DEFAULT TRUE
);

INSERT INTO identification_types (code, name, requires_dv) VALUES
    ('NIT', 'NIT',                             TRUE),
    ('CC',  'Cédula de Ciudadanía',            FALSE),
    ('CE',  'Cédula de Extranjería',           FALSE),
    ('TI',  'Tarjeta de Identidad',            FALSE),
    ('PP',  'Pasaporte',                       FALSE),
    ('RUT', 'Registro Único Tributario (RUT)', FALSE);

-- 3. Departamentos (DIVIPOLA)
CREATE TABLE departments (
    id   UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(10)  NOT NULL UNIQUE,
    name VARCHAR(30) NOT NULL
);

INSERT INTO departments (code, name) VALUES ('08', 'Atlántico');

-- 4. Ciudades (DIVIPOLA)
CREATE TABLE cities (
    id            UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    code          VARCHAR(10)  NOT NULL,
    name          VARCHAR(30) NOT NULL,
    department_id UUID         NOT NULL REFERENCES departments(id)
);

INSERT INTO cities (code, name, department_id)
SELECT '08001', 'Barranquilla', id FROM departments WHERE code = '08';

-- 5. Nuevas columnas en third_parties
ALTER TABLE third_parties
    ADD COLUMN tp_category_id          UUID REFERENCES third_party_categories(id),
    ADD COLUMN identification_type_id  UUID REFERENCES identification_types(id),
    ADD COLUMN dv                      VARCHAR(2),
    ADD COLUMN last_name               VARCHAR(150),
    ADD COLUMN common_name             VARCHAR(200),
    ADD COLUMN phone                   VARCHAR(50),
    ADD COLUMN address                 VARCHAR(255),
    ADD COLUMN department_id           UUID REFERENCES departments(id),
    ADD COLUMN city_id                 UUID REFERENCES cities(id),
    ADD COLUMN website                 VARCHAR(255),
    ADD COLUMN entry_date              DATE,
    ADD COLUMN credit_days             INTEGER      NOT NULL DEFAULT 30,
    -- Contacto de la empresa
    ADD COLUMN contact_name            VARCHAR(200),
    ADD COLUMN contact_phone           VARCHAR(50),
    ADD COLUMN contact_address         VARCHAR(255),
    ADD COLUMN contact_email           VARCHAR(150),
    -- Facturación electrónica y responsabilidad fiscal
    ADD COLUMN tax_contact_first_name  VARCHAR(100),
    ADD COLUMN tax_contact_last_name   VARCHAR(100),
    ADD COLUMN tax_email               VARCHAR(150),
    ADD COLUMN billing_phone           VARCHAR(50),
    ADD COLUMN is_gran_contribuyente   BOOLEAN      NOT NULL DEFAULT FALSE,
    ADD COLUMN is_autoretenedor        BOOLEAN      NOT NULL DEFAULT FALSE,
    ADD COLUMN is_agente_retencion_iva BOOLEAN      NOT NULL DEFAULT FALSE,
    ADD COLUMN is_regimen_simple       BOOLEAN      NOT NULL DEFAULT FALSE,
    ADD COLUMN other_tax_resp          BOOLEAN      NOT NULL DEFAULT FALSE;

-- 6. Datos básicos de empleado (1:1, solo cuando base_type = EMPLOYEE)
CREATE TABLE employee_details (
    id                    UUID    PRIMARY KEY REFERENCES third_parties(id) ON DELETE CASCADE,
    position              VARCHAR(100),
    cost_center           VARCHAR(100),
    work_center           VARCHAR(100),
    gender                VARCHAR(20),
    civil_status          VARCHAR(20),
    sales_group           VARCHAR(100),
    birth_date            DATE,
    birth_place           VARCHAR(100),
    military_id           VARCHAR(50),
    is_foreigner          BOOLEAN NOT NULL DEFAULT FALSE,
    nat_resident_exterior BOOLEAN NOT NULL DEFAULT FALSE,
    is_declarant          BOOLEAN NOT NULL DEFAULT FALSE,
    associated_seller     VARCHAR(100),
    requires_endowment    BOOLEAN NOT NULL DEFAULT FALSE,
    is_senior             BOOLEAN NOT NULL DEFAULT FALSE
);
