CREATE TABLE IF NOT EXISTS company_config (
    id               BIGINT PRIMARY KEY DEFAULT 1,
    company_name     VARCHAR(255) NOT NULL,
    nit              VARCHAR(20)  NOT NULL,
    address          VARCHAR(255),
    phone            VARCHAR(30),
    email            VARCHAR(255),
    economic_activity VARCHAR(255),
    tax_regime       VARCHAR(100),
    currency         VARCHAR(3)   NOT NULL DEFAULT 'COP',
    main_warehouse_id UUID        REFERENCES warehouses(id),
    logo_url         VARCHAR(500),
    created_at       TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CHECK (id = 1)
);
