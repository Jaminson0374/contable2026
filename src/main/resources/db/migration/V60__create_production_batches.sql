CREATE TABLE IF NOT EXISTS production_batches (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    formula_id UUID NOT NULL REFERENCES product_formulas(id),
    quantity_produced NUMERIC(15,4) NOT NULL CHECK (quantity_produced > 0),
    expected_quantity NUMERIC(15,4) NOT NULL,
    direct_material_cost NUMERIC(15,2) NOT NULL DEFAULT 0,
    direct_labor_cost NUMERIC(15,2) NOT NULL DEFAULT 0,
    overhead_cost NUMERIC(15,2) NOT NULL DEFAULT 0,
    total_cost NUMERIC(15,2) NOT NULL,
    unit_cost NUMERIC(15,4) NOT NULL,
    shrinkage_quantity NUMERIC(15,4) NOT NULL DEFAULT 0,
    shrinkage_cost NUMERIC(15,2) NOT NULL DEFAULT 0,
    notes TEXT,
    created_by UUID REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
