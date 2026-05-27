CREATE TYPE third_party_type_enum AS ENUM ('CLIENT', 'SUPPLIER', 'BOTH');
CREATE TYPE person_type_enum      AS ENUM ('NATURAL', 'JURIDICA');
CREATE TYPE tax_regime_enum       AS ENUM ('SIMPLE', 'ORDINARIO');

CREATE TABLE price_lists (
    id          UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_active   BOOLEAN      NOT NULL DEFAULT true,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

INSERT INTO price_lists (name, description) VALUES
    ('Público General', 'Lista de precios para venta al detal'),
    ('Mayorista',       'Lista de precios para distribuidores y restaurantes'),
    ('Restaurante',     'Lista de precios preferencial para restaurantes frecuentes');

CREATE TABLE third_parties (
    id                   UUID                  PRIMARY KEY DEFAULT gen_random_uuid(),
    num_identification   VARCHAR(40)           NOT NULL UNIQUE,
    name                 VARCHAR(200)          NOT NULL,
    type                 third_party_type_enum NOT NULL DEFAULT 'CLIENT',
    price_list_id        UUID                  REFERENCES price_lists(id),
    credit_limit         NUMERIC(15,2)         NOT NULL DEFAULT 0.00,
    current_balance      NUMERIC(15,2)         NOT NULL DEFAULT 0.00,
    person_type          person_type_enum      NOT NULL,
    tax_regime           tax_regime_enum       NOT NULL,
    -- Responsabilidades DIAN: ej. ["IVA", "NO_RESPONSABLE_IVA"]
    tax_responsibilities JSONB                 NOT NULL DEFAULT '[]',
    city_code            VARCHAR(10),
    dian_classification  VARCHAR(10),
    is_active            BOOLEAN               NOT NULL DEFAULT true,
    created_at           TIMESTAMPTZ           NOT NULL DEFAULT NOW(),
    updated_at           TIMESTAMPTZ           NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_third_parties_num_identification ON third_parties(num_identification);
CREATE INDEX idx_third_parties_type      ON third_parties(type);
CREATE INDEX idx_third_parties_is_active ON third_parties(is_active);

CREATE TABLE product_prices (
    id            UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    price_list_id UUID          NOT NULL REFERENCES price_lists(id) ON DELETE CASCADE,
    product_id    UUID          NOT NULL REFERENCES products(id)    ON DELETE CASCADE,
    price         NUMERIC(15,2) NOT NULL CHECK (price >= 0),
    last_update   TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    UNIQUE(price_list_id, product_id)
);

CREATE INDEX idx_product_prices_product ON product_prices(product_id);
