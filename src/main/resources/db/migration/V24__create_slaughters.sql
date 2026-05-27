-- ============================================================
-- V24: Tabla de faena / sacrificio
-- Registra el resultado del proceso de sacrificio.
-- Relación 1:1 con animal (uq_slaughter_animal) y 1:1 con batch.
-- Solo se acepta source_type = 'MANUAL' en este slice.
-- ============================================================

CREATE TABLE slaughters (
    id                UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    animal_id         UUID           NOT NULL,
    carcass_weight    NUMERIC(10,3)  NOT NULL CHECK (carcass_weight > 0),
    yield_percentage  NUMERIC(6,2)   NOT NULL,
    slaughter_date    DATE           NOT NULL DEFAULT CURRENT_DATE,
    invima_plant      VARCHAR(100)   NOT NULL,
    inspector_id      UUID           NOT NULL REFERENCES third_parties(id),
    source_type       VARCHAR(20)    NOT NULL
                      CHECK (source_type IN ('MANUAL','AUTOMATIC')),
    justification     VARCHAR(300),
    purchase_cost     NUMERIC(15,2)  NOT NULL CHECK (purchase_cost > 0),
    batch_id          UUID           NOT NULL,
    notes             TEXT,
    created_by        UUID           NOT NULL REFERENCES users(id),
    created_at        TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    version           BIGINT         NOT NULL DEFAULT 0,

    -- 1:1 garantizado: un animal solo puede sacrificarse una vez
    CONSTRAINT uq_slaughter_animal UNIQUE(animal_id),
    -- 1:1 con el lote generado
    CONSTRAINT uq_slaughter_batch  UNIQUE(batch_id),
    -- FK al animal
    CONSTRAINT fk_slaughter_animal FOREIGN KEY(animal_id) REFERENCES animals(id)
);

CREATE INDEX idx_slaughters_animal    ON slaughters(animal_id);
CREATE INDEX idx_slaughters_batch     ON slaughters(batch_id);
CREATE INDEX idx_slaughters_date      ON slaughters(slaughter_date DESC);
CREATE INDEX idx_slaughters_inspector ON slaughters(inspector_id);
