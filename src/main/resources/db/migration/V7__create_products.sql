CREATE TYPE product_category_enum AS ENUM ('CARNICO', 'INSUMO', 'GENERAL');
CREATE TYPE unit_of_measure_enum AS ENUM ('KG', 'UNIT', 'LB');
CREATE TYPE tax_category_enum    AS ENUM ('EXENTO', 'GRAVADO_5', 'GRAVADO_19');

CREATE TABLE products (
    id               UUID                  PRIMARY KEY DEFAULT gen_random_uuid(),
    product_code     VARCHAR(50)           NOT NULL UNIQUE,
    name             VARCHAR(200)          NOT NULL,
    category         product_category_enum NOT NULL,
    is_transformable BOOLEAN               NOT NULL DEFAULT false,
    tax_rate         NUMERIC(5,2)          NOT NULL DEFAULT 0.00,
    tax_category     tax_category_enum     NOT NULL DEFAULT 'EXENTO',
    unit_of_measure  unit_of_measure_enum  NOT NULL DEFAULT 'KG',
    image_url        VARCHAR(500),
    is_active        BOOLEAN               NOT NULL DEFAULT true,
    created_at       TIMESTAMPTZ           NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ           NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_products_category   ON products(category);
CREATE INDEX idx_products_is_active  ON products(is_active);
CREATE INDEX idx_products_code       ON products(product_code);
