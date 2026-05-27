-- ============================================================
-- V19: Sub-tablas del módulo Productos
-- Bodegas asignadas, proveedores, imágenes,
-- promociones y precios por lista
-- ============================================================

-- ──────────────────────────────────────────────────────────
-- 1. Bodegas y ubicaciones asignadas al producto
--    (El usuario define en qué bodega y ubicación se almacena
--     y cuál es la bodega predeterminada)
-- ──────────────────────────────────────────────────────────
CREATE TABLE product_warehouses (
    id                 UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id         UUID         NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    warehouse_id       UUID         NOT NULL REFERENCES warehouses(id),
    location_id        UUID         REFERENCES warehouse_locations(id) ON DELETE SET NULL,
    unit_of_measure_id UUID         REFERENCES units_of_measure(id) ON DELETE SET NULL,
    is_default         BOOLEAN      NOT NULL DEFAULT false,
    created_at         TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    UNIQUE (product_id, warehouse_id)
);

CREATE INDEX idx_prod_wh_product   ON product_warehouses(product_id);
CREATE INDEX idx_prod_wh_warehouse ON product_warehouses(warehouse_id);

-- Garantiza que solo una bodega por producto sea la predeterminada
-- (Se implementa a nivel de aplicación; la restricción parcial es informativa)
CREATE UNIQUE INDEX idx_prod_wh_default
    ON product_warehouses(product_id)
    WHERE is_default = true;

-- ──────────────────────────────────────────────────────────
-- 2. Proveedores del producto
--    (Proveedor: combobox, Principal: checkbox)
-- ──────────────────────────────────────────────────────────
CREATE TABLE product_suppliers (
    id                  UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id          UUID          NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    supplier_id         UUID          NOT NULL REFERENCES third_parties(id),
    supplier_reference  VARCHAR(100),
    unit_cost           NUMERIC(15,4),
    is_main             BOOLEAN       NOT NULL DEFAULT false,
    created_at          TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    UNIQUE (product_id, supplier_id)
);

CREATE INDEX idx_prod_sup_product  ON product_suppliers(product_id);
CREATE INDEX idx_prod_sup_supplier ON product_suppliers(supplier_id);

CREATE UNIQUE INDEX idx_prod_sup_main
    ON product_suppliers(product_id)
    WHERE is_main = true;

-- ──────────────────────────────────────────────────────────
-- 3. Imágenes del producto (máx. 4 imágenes, 2MB c/u)
--    Se almacena la ruta/referencia, nunca BLOB en BD
-- ──────────────────────────────────────────────────────────
CREATE TABLE product_images (
    id            UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id    UUID        NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    image_url     VARCHAR(500) NOT NULL,
    display_order SMALLINT     NOT NULL DEFAULT 0
                               CHECK (display_order BETWEEN 0 AND 3),
    -- 0=imagen principal (thumbnail), 1-3=imágenes adicionales
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_prod_images_product ON product_images(product_id);
CREATE UNIQUE INDEX idx_prod_images_order ON product_images(product_id, display_order);

-- ──────────────────────────────────────────────────────────
-- 4. Promociones del producto
--    (Historial a la derecha, formulario a la izquierda)
-- ──────────────────────────────────────────────────────────
CREATE TABLE product_promotions (
    id            UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id    UUID          NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    name          VARCHAR(200)  NOT NULL,
    discount_pct  NUMERIC(5,2)  NOT NULL CHECK (discount_pct BETWEEN 0 AND 100),
    start_date    DATE          NOT NULL,
    end_date      DATE          NOT NULL,
    is_active     BOOLEAN       NOT NULL DEFAULT false,
    created_at    TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_promo_dates CHECK (end_date >= start_date)
);

CREATE INDEX idx_prod_promo_product ON product_promotions(product_id);
CREATE INDEX idx_prod_promo_dates   ON product_promotions(start_date, end_date);

-- Solo una promoción activa por producto
CREATE UNIQUE INDEX idx_prod_promo_active
    ON product_promotions(product_id)
    WHERE is_active = true;

-- ──────────────────────────────────────────────────────────
-- 5. Precios por lista de precios (Pv1=Normal, Pv2=Mayorista, Pv3=Crédito)
--    Precio = Costo + Utilidad (calculado y guardado por lista)
-- ──────────────────────────────────────────────────────────
CREATE TABLE product_price_entries (
    id             UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id     UUID          NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    price_list_id  UUID          NOT NULL REFERENCES price_lists(id),
    price          NUMERIC(15,4) NOT NULL DEFAULT 0,
    profit_margin  NUMERIC(5,2)  NOT NULL DEFAULT 0,
    last_updated   TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    UNIQUE (product_id, price_list_id)
);

CREATE INDEX idx_price_entries_product    ON product_price_entries(product_id);
CREATE INDEX idx_price_entries_price_list ON product_price_entries(price_list_id);
