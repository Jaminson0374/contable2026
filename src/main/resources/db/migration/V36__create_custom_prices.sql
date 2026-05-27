CREATE TABLE custom_prices (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id UUID NOT NULL REFERENCES third_parties(id),
    product_id UUID NOT NULL REFERENCES products(id),
    price DECIMAL(19,2) NOT NULL,
    tax_type VARCHAR(20) NOT NULL,
    tax_rate DECIMAL(5,2) NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(client_id, product_id)
);

CREATE INDEX idx_custom_prices_client ON custom_prices(client_id);
CREATE INDEX idx_custom_prices_product ON custom_prices(product_id);
