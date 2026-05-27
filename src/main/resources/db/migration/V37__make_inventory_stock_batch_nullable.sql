ALTER TABLE inventory_stock ALTER COLUMN batch_id DROP NOT NULL;

DROP INDEX IF EXISTS idx_stock_batch;

CREATE INDEX idx_stock_batch ON inventory_stock(batch_id);
