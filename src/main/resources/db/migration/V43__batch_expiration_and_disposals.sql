ALTER TABLE batches ADD COLUMN IF NOT EXISTS expiration_date DATE;

CREATE TABLE stock_disposals (
    id              UUID PRIMARY KEY,
    product_id      UUID         NOT NULL REFERENCES products(id),
    batch_id        UUID         REFERENCES batches(id),
    warehouse_id    UUID         NOT NULL REFERENCES warehouses(id),
    disposal_type   VARCHAR(30)  NOT NULL CHECK (disposal_type IN ('SANITARIO','RESIDUO_VENDIBLE','MERMA_PROCESO')),
    quantity        NUMERIC(15,4) NOT NULL CHECK (quantity > 0),
    unit_cost       NUMERIC(15,6) NOT NULL DEFAULT 0,
    reason          TEXT         NOT NULL,
    created_by      VARCHAR(100),
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_sd_product   ON stock_disposals(product_id);
CREATE INDEX idx_sd_type      ON stock_disposals(disposal_type);
CREATE INDEX idx_sd_created   ON stock_disposals(created_at);
