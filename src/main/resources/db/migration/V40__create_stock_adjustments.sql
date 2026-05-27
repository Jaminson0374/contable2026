CREATE TABLE stock_adjustments (
    id              UUID PRIMARY KEY,
    product_id      UUID         NOT NULL REFERENCES products(id),
    batch_id        UUID         REFERENCES batches(id),
    warehouse_id    UUID         NOT NULL REFERENCES warehouses(id),
    adjustment_type VARCHAR(30)  NOT NULL CHECK (adjustment_type IN ('PHYSICAL_COUNT','DAMAGE','EXPIRATION','THEFT','OTHER')),
    quantity_before NUMERIC(15,4) NOT NULL,
    quantity_after  NUMERIC(15,4) NOT NULL,
    unit_cost       NUMERIC(15,6) NOT NULL DEFAULT 0,
    reason          TEXT         NOT NULL,
    created_by      VARCHAR(100),
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_sa_product    ON stock_adjustments(product_id);
CREATE INDEX idx_sa_batch      ON stock_adjustments(batch_id);
CREATE INDEX idx_sa_warehouse  ON stock_adjustments(warehouse_id);
CREATE INDEX idx_sa_type       ON stock_adjustments(adjustment_type);
CREATE INDEX idx_sa_created_at ON stock_adjustments(created_at);
