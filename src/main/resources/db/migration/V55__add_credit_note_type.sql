-- ALTER the CHECK constraint on sales_documents.type to allow CREDIT_NOTE
ALTER TABLE sales_documents DROP CONSTRAINT IF EXISTS sales_documents_type_check;
ALTER TABLE sales_documents ADD CONSTRAINT sales_documents_type_check CHECK (type IN ('QUOTE','ORDER','INVOICE','CREDIT_NOTE'));
-- Add reason column for devolution reason
ALTER TABLE sales_documents ADD COLUMN IF NOT EXISTS reason VARCHAR(500);
