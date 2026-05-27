-- Recalculate totals for all DRAFT sales_documents by summing their sale_items
-- Idempotent: safe to run multiple times

WITH item_sums AS (
    SELECT
        si.document_id,
        COALESCE(SUM(si.subtotal), 0)                  AS total_net,
        COALESCE(SUM(CASE WHEN si.tax_rate = 0  THEN si.tax_amount ELSE 0 END), 0) AS total_tax_0,
        COALESCE(SUM(CASE WHEN si.tax_rate = 5  THEN si.tax_amount ELSE 0 END), 0) AS total_tax_5,
        COALESCE(SUM(CASE WHEN si.tax_rate = 8  THEN si.tax_amount ELSE 0 END), 0) AS total_tax_8,
        COALESCE(SUM(CASE WHEN si.tax_rate = 19 THEN si.tax_amount ELSE 0 END), 0) AS total_tax_19,
        COALESCE(SUM(si.subtotal), 0)
            + COALESCE(SUM(CASE WHEN si.tax_rate = 0  THEN si.tax_amount ELSE 0 END), 0)
            + COALESCE(SUM(CASE WHEN si.tax_rate = 5  THEN si.tax_amount ELSE 0 END), 0)
            + COALESCE(SUM(CASE WHEN si.tax_rate = 8  THEN si.tax_amount ELSE 0 END), 0)
            + COALESCE(SUM(CASE WHEN si.tax_rate = 19 THEN si.tax_amount ELSE 0 END), 0) AS total_amount
    FROM sales_items si
    GROUP BY si.document_id
)
UPDATE sales_documents sd
SET
    total_net    = item_sums.total_net,
    total_tax_0  = item_sums.total_tax_0,
    total_tax_5  = item_sums.total_tax_5,
    total_tax_8  = item_sums.total_tax_8,
    total_tax_19 = item_sums.total_tax_19,
    total_amount = item_sums.total_amount
FROM item_sums
WHERE sd.id = item_sums.document_id
  AND sd.status = 'DRAFT';
