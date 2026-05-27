CREATE TYPE batch_status_enum AS ENUM ('OPEN', 'PROCESSING', 'CLOSED');

CREATE TABLE batches (
    id            UUID               PRIMARY KEY DEFAULT gen_random_uuid(),
    supplier_id   UUID               NOT NULL REFERENCES third_parties(id),
    warehouse_id  UUID               NOT NULL REFERENCES warehouses(id),
    entry_date    DATE               NOT NULL DEFAULT CURRENT_DATE,
    initial_weight NUMERIC(10,3)     NOT NULL CHECK (initial_weight > 0),
    purchase_cost  NUMERIC(15,2)     NOT NULL CHECK (purchase_cost >= 0),
    status        batch_status_enum  NOT NULL DEFAULT 'OPEN',
    notes         TEXT,
    created_by    UUID               NOT NULL REFERENCES users(id),
    created_at    TIMESTAMPTZ        NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ        NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_batches_supplier   ON batches(supplier_id);
CREATE INDEX idx_batches_warehouse  ON batches(warehouse_id);
CREATE INDEX idx_batches_status     ON batches(status);
CREATE INDEX idx_batches_entry_date ON batches(entry_date DESC);
