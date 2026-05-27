CREATE TABLE IF NOT EXISTS production_orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_number VARCHAR(30) NOT NULL UNIQUE,
    formula_id UUID NOT NULL REFERENCES products(id),
    planned_quantity NUMERIC(15,4) NOT NULL CHECK (planned_quantity > 0),
    planned_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PLANNED'
        CHECK (status IN ('PLANNED','APPROVED','IN_PROGRESS','COMPLETED','CANCELLED')),
    warehouse_id UUID NOT NULL REFERENCES warehouses(id),
    machinery_id UUID REFERENCES machinery(id),
    notes TEXT,
    created_by VARCHAR(100),
    approved_by VARCHAR(100),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    approved_at TIMESTAMPTZ
);
CREATE INDEX IF NOT EXISTS idx_po_status ON production_orders(status);
CREATE INDEX IF NOT EXISTS idx_po_warehouse ON production_orders(warehouse_id);
CREATE INDEX IF NOT EXISTS idx_po_formula ON production_orders(formula_id);
CREATE INDEX IF NOT EXISTS idx_po_planned_date ON production_orders(planned_date);

ALTER TABLE production_batches ADD COLUMN IF NOT EXISTS order_id UUID REFERENCES production_orders(id);
ALTER TABLE production_batches ADD COLUMN IF NOT EXISTS status VARCHAR(20) DEFAULT 'OPEN'
    CHECK (status IN ('OPEN','IN_PROGRESS','CLOSED','CANCELLED'));
