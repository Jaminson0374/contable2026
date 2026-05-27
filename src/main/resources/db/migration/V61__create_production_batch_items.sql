CREATE TABLE IF NOT EXISTS production_batch_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    batch_id UUID NOT NULL REFERENCES production_batches(id) ON DELETE CASCADE,
    component_product_id UUID NOT NULL REFERENCES products(id),
    planned_quantity NUMERIC(15,4) NOT NULL,
    actual_quantity NUMERIC(15,4) NOT NULL,
    unit_cost NUMERIC(15,4) NOT NULL,
    total_cost NUMERIC(15,2) NOT NULL,
    kardex_movement_id UUID REFERENCES inventory_movements(id)
);
CREATE INDEX IF NOT EXISTS idx_pbi_batch ON production_batch_items(batch_id);
