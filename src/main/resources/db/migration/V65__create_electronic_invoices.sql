CREATE TABLE IF NOT EXISTS electronic_invoices (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sales_document_id UUID NOT NULL UNIQUE REFERENCES sales_documents(id),
    source_document_id UUID REFERENCES sales_documents(id),
    cufe VARCHAR(200) UNIQUE,
    qr_code TEXT,
    provider_response JSONB,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING_SEND',
    sent_at TIMESTAMPTZ,
    response_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS dian_sync_queue (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    electronic_invoice_id UUID NOT NULL REFERENCES electronic_invoices(id),
    attempt_count INT NOT NULL DEFAULT 0,
    max_attempts INT NOT NULL DEFAULT 5,
    next_attempt_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    last_error TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
