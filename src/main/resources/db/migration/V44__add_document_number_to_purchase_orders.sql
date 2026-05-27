ALTER TABLE purchase_orders ADD COLUMN IF NOT EXISTS document_number VARCHAR(30);
CREATE INDEX IF NOT EXISTS idx_po_doc_number ON purchase_orders(document_number);
