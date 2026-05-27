-- Hibernate 6 binds @Enumerated(EnumType.STRING) as VARCHAR. PostgreSQL rejects
-- implicit cast from varchar to custom enum types on INSERT/UPDATE.
-- Converting columns to VARCHAR fixes the type mismatch permanently.
-- Java enums continue to enforce valid values at the application layer.

-- Step 1: Drop DEFAULT expressions that reference the enum types.
-- PostgreSQL blocks ALTER TYPE on columns that have typed defaults.
ALTER TABLE products      ALTER COLUMN tax_category    DROP DEFAULT;
ALTER TABLE products      ALTER COLUMN unit_of_measure DROP DEFAULT;
ALTER TABLE third_parties ALTER COLUMN type            DROP DEFAULT;
ALTER TABLE batches       ALTER COLUMN status          DROP DEFAULT;

-- Step 2: Drop views that depend on enum columns.
DROP VIEW IF EXISTS v_available_stock;

-- Step 3: Convert enum columns to VARCHAR.
ALTER TABLE products
    ALTER COLUMN category        TYPE VARCHAR(30) USING category::text,
    ALTER COLUMN tax_category    TYPE VARCHAR(20) USING tax_category::text,
    ALTER COLUMN unit_of_measure TYPE VARCHAR(10) USING unit_of_measure::text;

ALTER TABLE warehouses
    ALTER COLUMN warehouse_type TYPE VARCHAR(20) USING warehouse_type::text;

ALTER TABLE third_parties
    ALTER COLUMN type        TYPE VARCHAR(20) USING type::text,
    ALTER COLUMN person_type TYPE VARCHAR(10) USING person_type::text,
    ALTER COLUMN tax_regime  TYPE VARCHAR(20) USING tax_regime::text;

ALTER TABLE batches
    ALTER COLUMN status TYPE VARCHAR(20) USING status::text;

-- Step 4: Restore defaults as plain string literals.
ALTER TABLE products      ALTER COLUMN tax_category    SET DEFAULT 'EXENTO';
ALTER TABLE products      ALTER COLUMN unit_of_measure SET DEFAULT 'KG';
ALTER TABLE third_parties ALTER COLUMN type            SET DEFAULT 'CLIENT';
ALTER TABLE batches       ALTER COLUMN status          SET DEFAULT 'OPEN';

-- Step 5: Drop the now-unused custom enum types.
DROP TYPE IF EXISTS product_category_enum;
DROP TYPE IF EXISTS unit_of_measure_enum;
DROP TYPE IF EXISTS tax_category_enum;
DROP TYPE IF EXISTS warehouse_type_enum;
DROP TYPE IF EXISTS third_party_type_enum;
DROP TYPE IF EXISTS person_type_enum;
DROP TYPE IF EXISTS tax_regime_enum;
DROP TYPE IF EXISTS batch_status_enum;

-- Step 6: Recreate the view (column is now VARCHAR, query unchanged).
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
