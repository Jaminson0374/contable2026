CREATE TABLE inventory_stock (
    id                 UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id         UUID          NOT NULL REFERENCES products(id),
    batch_id           UUID          NOT NULL REFERENCES batches(id),
    warehouse_id       UUID          NOT NULL REFERENCES warehouses(id),
    current_quantity   NUMERIC(12,3) NOT NULL DEFAULT 0 CHECK (current_quantity >= 0),
    committed_quantity NUMERIC(12,3) NOT NULL DEFAULT 0 CHECK (committed_quantity >= 0),
    unit_cost          NUMERIC(15,4) NOT NULL DEFAULT 0 CHECK (unit_cost >= 0),
    created_at         TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_at         TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    UNIQUE(product_id, batch_id, warehouse_id)
);

CREATE INDEX idx_stock_product   ON inventory_stock(product_id);
CREATE INDEX idx_stock_batch     ON inventory_stock(batch_id);
CREATE INDEX idx_stock_warehouse ON inventory_stock(warehouse_id);

-- Vista para consulta rápida de stock disponible por producto y bodega
CREATE VIEW v_available_stock AS
SELECT
    s.product_id,
    p.name              AS product_name,
    p.unit_of_measure,
    s.warehouse_id,
    w.name              AS warehouse_name,
    SUM(s.current_quantity - s.committed_quantity) AS available_quantity,
    SUM(s.current_quantity)                         AS total_quantity,
    AVG(s.unit_cost)                                AS avg_unit_cost
FROM inventory_stock s
JOIN products   p ON p.id = s.product_id
JOIN warehouses w ON w.id = s.warehouse_id
WHERE s.current_quantity > 0
GROUP BY s.product_id, p.name, p.unit_of_measure, s.warehouse_id, w.name;
