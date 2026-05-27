CREATE TABLE IF NOT EXISTS product_formulas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    parent_product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    component_product_id UUID NOT NULL REFERENCES products(id),
    quantity NUMERIC(15,4) NOT NULL CHECK (quantity > 0),
    unit_of_measure_id UUID REFERENCES units_of_measure(id),
    sequence_number INT NOT NULL DEFAULT 0,
    notes TEXT,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(parent_product_id, component_product_id)
);
