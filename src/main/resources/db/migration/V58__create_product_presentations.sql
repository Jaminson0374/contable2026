CREATE TABLE IF NOT EXISTS product_presentations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    code VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    unit_of_measure_id UUID NOT NULL REFERENCES units_of_measure(id),
    conversion_factor NUMERIC(15,4) NOT NULL CHECK (conversion_factor > 0),
    sale_price NUMERIC(15,4),
    is_default BOOLEAN NOT NULL DEFAULT false,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(product_id, code)
);
CREATE INDEX IF NOT EXISTS idx_pp_product ON product_presentations(product_id);
