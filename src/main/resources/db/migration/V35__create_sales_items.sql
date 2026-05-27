CREATE TABLE sales_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    document_id UUID NOT NULL REFERENCES sales_documents(id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES products(id),
    quantity DECIMAL(19,3) NOT NULL,
    unit_price DECIMAL(19,2) NOT NULL,
    tax_type VARCHAR(20) NOT NULL,
    tax_rate DECIMAL(5,2) NOT NULL,
    tax_amount DECIMAL(19,2) NOT NULL DEFAULT 0,
    subtotal DECIMAL(19,2) NOT NULL,
    line_number INT NOT NULL,
    batch_id UUID REFERENCES batches(id)
);

CREATE INDEX idx_sales_items_document ON sales_items(document_id);
CREATE INDEX idx_sales_items_product ON sales_items(product_id);
CREATE INDEX idx_sales_items_batch ON sales_items(batch_id);
