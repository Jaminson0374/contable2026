CREATE TABLE inventory_movements (
    id              UUID PRIMARY KEY,
    product_id      UUID         NOT NULL REFERENCES products(id),
    batch_id        UUID         REFERENCES batches(id),
    warehouse_id    UUID         NOT NULL REFERENCES warehouses(id),
    movement_type   VARCHAR(20)  NOT NULL CHECK (movement_type IN ('ENTRY','EXIT','ADJUSTMENT','TRANSFER_IN','TRANSFER_OUT','DISPOSAL')),
    quantity        NUMERIC(15,4) NOT NULL,
    unit_cost       NUMERIC(15,6) NOT NULL DEFAULT 0,
    previous_qty    NUMERIC(15,4) NOT NULL,
    new_qty         NUMERIC(15,4) NOT NULL,
    reference_type  VARCHAR(50),
    reference_id    UUID,
    notes           TEXT,
    created_by      VARCHAR(100),
    created_at      TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_im_product     ON inventory_movements(product_id);
CREATE INDEX idx_im_batch       ON inventory_movements(batch_id);
CREATE INDEX idx_im_warehouse   ON inventory_movements(warehouse_id);
CREATE INDEX idx_im_type        ON inventory_movements(movement_type);
CREATE INDEX idx_im_created_at  ON inventory_movements(created_at);
CREATE INDEX idx_im_reference   ON inventory_movements(reference_type, reference_id);
