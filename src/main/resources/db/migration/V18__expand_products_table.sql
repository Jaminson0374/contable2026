-- ============================================================
-- V18: Expansión de la tabla products
-- Agrega todos los campos requeridos por el nuevo formulario.
-- Se mantienen los campos anteriores para compatibilidad.
-- ============================================================

-- Actualizar vista dependiente antes de modificar la tabla
DROP VIEW IF EXISTS v_available_stock;

-- ──────────────────────────────────────────────────────────
-- NUEVOS CAMPOS: Identificación y clasificación
-- ──────────────────────────────────────────────────────────
ALTER TABLE products
    ADD COLUMN product_type_id     UUID REFERENCES product_types(id),
    ADD COLUMN product_state_id    UUID REFERENCES product_states(id),
    ADD COLUMN brand_id            UUID REFERENCES brands(id),
    ADD COLUMN model_id            UUID REFERENCES product_models(id),
    ADD COLUMN product_category_id UUID REFERENCES product_categories(id),
    ADD COLUMN product_group_id    UUID REFERENCES product_groups(id),
    ADD COLUMN unit_of_measure_id  UUID REFERENCES units_of_measure(id),
    ADD COLUMN barcode             VARCHAR(100),
    ADD COLUMN reference           VARCHAR(100),
    ADD COLUMN description         TEXT;

-- ──────────────────────────────────────────────────────────
-- NUEVOS CAMPOS: Costos, precios e impuestos
-- ──────────────────────────────────────────────────────────
ALTER TABLE products
    ADD COLUMN cost_price     NUMERIC(15,4) NOT NULL DEFAULT 0,
    ADD COLUMN profit_margin  NUMERIC(5,2)  NOT NULL DEFAULT 0,
    -- Porcentaje de utilidad. Precio Venta = Costo × (1 + margen/100) × (1 + iva/100)
    ADD COLUMN tax_type       VARCHAR(10)   NOT NULL DEFAULT 'EXENTO'
                              CHECK (tax_type IN ('EXENTO','IVA_5','IVA_8','IVA_19')),
    ADD COLUMN sale_price     NUMERIC(15,4) NOT NULL DEFAULT 0;
    -- Calculado automáticamente: no editable directamente

-- ──────────────────────────────────────────────────────────
-- NUEVOS CAMPOS: Inventario y opciones de verificación
-- ──────────────────────────────────────────────────────────
ALTER TABLE products
    ADD COLUMN costing_method        VARCHAR(30) NOT NULL DEFAULT 'PROMEDIO_PONDERADO'
                                     CHECK (costing_method IN (
                                         'PEPS','PROMEDIO_PONDERADO',
                                         'ESTANDAR','IDENTIFICACION_ESPECIFICA','YIELD_COSTING'
                                     )),
    ADD COLUMN initial_stock         NUMERIC(15,4) NOT NULL DEFAULT 0,
    ADD COLUMN min_stock             NUMERIC(15,4) NOT NULL DEFAULT 0,
    ADD COLUMN max_stock             NUMERIC(15,4) NOT NULL DEFAULT 0,
    ADD COLUMN manufactured_in_house BOOLEAN       NOT NULL DEFAULT false,
    ADD COLUMN cost_affecting_exp    BOOLEAN       NOT NULL DEFAULT false,
    ADD COLUMN manages_lots          BOOLEAN       NOT NULL DEFAULT false,
    ADD COLUMN is_perishable         BOOLEAN       NOT NULL DEFAULT false,
    ADD COLUMN belongs_to_product    BOOLEAN       NOT NULL DEFAULT false,
    -- true = este artículo es componente de otro (fórmula/combo)
    ADD COLUMN sell_below_min        BOOLEAN       NOT NULL DEFAULT false,
    ADD COLUMN is_inventoriable      BOOLEAN       NOT NULL DEFAULT true;

-- ──────────────────────────────────────────────────────────
-- NUEVOS CAMPOS: Especificaciones
-- ──────────────────────────────────────────────────────────
ALTER TABLE products
    ADD COLUMN serial_number   VARCHAR(100),
    ADD COLUMN origin_country  VARCHAR(100),
    ADD COLUMN specifications  TEXT;

-- ──────────────────────────────────────────────────────────
-- NUEVOS CAMPOS: Contabilización
-- ──────────────────────────────────────────────────────────
ALTER TABLE products
    ADD COLUMN income_account_id       UUID REFERENCES puc_accounts(id),
    ADD COLUMN inventory_account_id    UUID REFERENCES puc_accounts(id),
    ADD COLUMN cost_of_sales_acct_id   UUID REFERENCES puc_accounts(id);

-- ──────────────────────────────────────────────────────────
-- NUEVOS CAMPOS: Control de concurrencia (multiusuario)
-- ──────────────────────────────────────────────────────────
ALTER TABLE products
    ADD COLUMN version INTEGER NOT NULL DEFAULT 0;

-- ──────────────────────────────────────────────────────────
-- Índices para los nuevos campos de búsqueda frecuente
-- ──────────────────────────────────────────────────────────
CREATE INDEX idx_products_type          ON products(product_type_id);
CREATE INDEX idx_products_category_new  ON products(product_category_id);
CREATE INDEX idx_products_group         ON products(product_group_id);
CREATE INDEX idx_products_barcode       ON products(barcode);

-- ──────────────────────────────────────────────────────────
-- Restaurar la vista de stock disponible
-- ──────────────────────────────────────────────────────────
CREATE VIEW v_available_stock AS
SELECT
    s.product_id,
    p.name                                          AS product_name,
    COALESCE(u.code, p.unit_of_measure)             AS unit_of_measure,
    s.warehouse_id,
    w.name                                          AS warehouse_name,
    SUM(s.current_quantity - s.committed_quantity)  AS available_quantity,
    SUM(s.current_quantity)                         AS total_quantity,
    AVG(s.unit_cost)                                AS avg_unit_cost
FROM inventory_stock s
JOIN products         p ON p.id = s.product_id
JOIN warehouses       w ON w.id = s.warehouse_id
LEFT JOIN units_of_measure u ON u.id = p.unit_of_measure_id
WHERE s.current_quantity > 0
GROUP BY s.product_id, p.name, COALESCE(u.code, p.unit_of_measure),
         s.warehouse_id, w.name;
