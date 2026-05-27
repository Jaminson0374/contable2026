DROP VIEW IF EXISTS v_available_stock;

ALTER TABLE inventory_stock
    ALTER COLUMN unit_cost TYPE NUMERIC(15,6);

CREATE VIEW v_available_stock AS
SELECT
    s.product_id,
    p.name              AS product_name,
    p.unit_of_measure,
    s.warehouse_id,
    w.name              AS warehouse_name,
    SUM(s.current_quantity - s.committed_quantity) AS available_quantity,
    SUM(s.current_quantity)                         AS total_quantity,
    AVG(s.unit_cost)                               AS avg_unit_cost
FROM inventory_stock s
JOIN products   p ON p.id = s.product_id
JOIN warehouses w ON w.id = s.warehouse_id
WHERE s.current_quantity > 0
GROUP BY s.product_id, p.name, p.unit_of_measure, s.warehouse_id, w.name;
