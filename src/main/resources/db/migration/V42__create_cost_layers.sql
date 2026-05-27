CREATE TABLE cost_layers (
    id                  UUID PRIMARY KEY,
    product_id          UUID        NOT NULL REFERENCES products(id),
    batch_id            UUID        REFERENCES batches(id),
    warehouse_id        UUID        NOT NULL REFERENCES warehouses(id),
    remaining_quantity  NUMERIC(15,4) NOT NULL DEFAULT 0,
    unit_cost           NUMERIC(15,6) NOT NULL DEFAULT 0,
    entry_date          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    source_movement_id  UUID        REFERENCES inventory_movements(id)
);

CREATE INDEX idx_cl_product   ON cost_layers(product_id);
CREATE INDEX idx_cl_batch     ON cost_layers(batch_id);
CREATE INDEX idx_cl_warehouse ON cost_layers(warehouse_id);
CREATE INDEX idx_cl_entry     ON cost_layers(entry_date);
