CREATE TABLE stock_transfers (
    id                  UUID PRIMARY KEY,
    source_warehouse_id UUID        NOT NULL REFERENCES warehouses(id),
    target_warehouse_id UUID        NOT NULL REFERENCES warehouses(id),
    status              VARCHAR(20) NOT NULL CHECK (status IN ('DRAFT','CONFIRMED','CANCELLED')),
    notes               TEXT,
    created_by          VARCHAR(100),
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    confirmed_by        VARCHAR(100),
    confirmed_at        TIMESTAMPTZ
);

CREATE TABLE stock_transfer_items (
    id          UUID PRIMARY KEY,
    transfer_id UUID        NOT NULL REFERENCES stock_transfers(id) ON DELETE CASCADE,
    product_id  UUID        NOT NULL REFERENCES products(id),
    batch_id    UUID        REFERENCES batches(id),
    quantity    NUMERIC(15,4) NOT NULL CHECK (quantity > 0),
    unit_cost   NUMERIC(15,6) NOT NULL DEFAULT 0
);

CREATE INDEX idx_st_status    ON stock_transfers(status);
CREATE INDEX idx_st_source    ON stock_transfers(source_warehouse_id);
CREATE INDEX idx_st_target    ON stock_transfers(target_warehouse_id);
CREATE INDEX idx_sti_transfer ON stock_transfer_items(transfer_id);
