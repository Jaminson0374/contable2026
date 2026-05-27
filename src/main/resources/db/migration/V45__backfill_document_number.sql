-- Backfill document_number for existing purchase orders
-- Format: PO-YYYYMMDD-NNNN (sequential per date)
UPDATE purchase_orders po
SET document_number = sub.document_number
FROM (
    SELECT 
        id,
        order_date,
        'PO-' || TO_CHAR(order_date, 'YYYYMMDD') || '-' || 
        LPAD(ROW_NUMBER() OVER (PARTITION BY order_date ORDER BY created_at)::TEXT, 4, '0') 
        AS document_number
    FROM purchase_orders
    WHERE document_number IS NULL
) sub
WHERE po.id = sub.id;
