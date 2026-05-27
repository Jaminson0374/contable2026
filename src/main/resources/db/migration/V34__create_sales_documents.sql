CREATE TABLE sales_documents (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type VARCHAR(20) NOT NULL CHECK (type IN ('QUOTE','ORDER','INVOICE')),
    document_number VARCHAR(50) NOT NULL,
    client_id UUID NOT NULL REFERENCES third_parties(id),
    warehouse_id UUID NOT NULL REFERENCES warehouses(id),
    shift_id UUID REFERENCES shifts(id),
    cash_register_id UUID REFERENCES cash_registers(id),
    source_document_id UUID REFERENCES sales_documents(id),
    status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',
    total_net DECIMAL(19,2) NOT NULL DEFAULT 0,
    total_tax_0 DECIMAL(19,2) NOT NULL DEFAULT 0,
    total_tax_5 DECIMAL(19,2) NOT NULL DEFAULT 0,
    total_tax_8 DECIMAL(19,2) NOT NULL DEFAULT 0,
    total_tax_19 DECIMAL(19,2) NOT NULL DEFAULT 0,
    total_amount DECIMAL(19,2) NOT NULL DEFAULT 0,
    created_by UUID NOT NULL REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ
);

CREATE INDEX idx_sales_documents_type ON sales_documents(type);
CREATE INDEX idx_sales_documents_client ON sales_documents(client_id);
CREATE INDEX idx_sales_documents_status ON sales_documents(status);
CREATE INDEX idx_sales_documents_shift ON sales_documents(shift_id);
CREATE INDEX idx_sales_documents_source ON sales_documents(source_document_id);
CREATE INDEX idx_sales_documents_document_number ON sales_documents(document_number);
