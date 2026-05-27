-- ============================================================
-- V23: Tabla de registro de animales (ICA / INVIMA)
-- Registro de animales vivos que ingresan a la planta.
-- 1 Animal = 1 Slaughter = 1 Batch CANAL (relación 1:1:1)
-- ============================================================

CREATE TABLE animals (
    id               UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    ica_lot_number   VARCHAR(50)    NOT NULL UNIQUE,
    supplier_id      UUID           NOT NULL REFERENCES third_parties(id),
    species          VARCHAR(20)    NOT NULL
                     CHECK (species IN ('PORCINO','BOVINO','OVINO')),
    live_weight      NUMERIC(10,3)  NOT NULL CHECK (live_weight > 0),
    reception_date   DATE           NOT NULL DEFAULT CURRENT_DATE,
    status           VARCHAR(20)    NOT NULL DEFAULT 'RECEIVED'
                     CHECK (status IN ('RECEIVED','IN_SLAUGHTER','SLAUGHTERED')),
    notes            TEXT,
    created_by       UUID           NOT NULL REFERENCES users(id),
    created_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    version          BIGINT         NOT NULL DEFAULT 0
);

CREATE INDEX idx_animals_ica_lot   ON animals(ica_lot_number);
CREATE INDEX idx_animals_supplier  ON animals(supplier_id);
CREATE INDEX idx_animals_status    ON animals(status);
CREATE INDEX idx_animals_reception ON animals(reception_date DESC);

-- ============================================================
-- Seed: Producto CANAL (Materia Prima)
-- Producto inventariable que representa la canal resultante
-- después del sacrificio. Es transformable (origen del desposte).
-- Las referencias de catálogo (product_type, etc.) son NULL — se
-- completan por el operador luego si es necesario.
-- ============================================================
INSERT INTO products (product_code, name, is_inventoriable, manufactured_in_house)
VALUES ('CANAL', 'Canal / Materia Prima', true, false);
