CREATE TABLE accounts_receivable (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES third_parties(id),
    document_id UUID NOT NULL REFERENCES sales_documents(id),
    total_amount NUMERIC(15,2) NOT NULL,
    paid_amount NUMERIC(15,2) NOT NULL DEFAULT 0,
    outstanding NUMERIC(15,2) NOT NULL,
    due_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('OPEN','PARTIAL','PAID','OVERDUE')),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_ar_client ON accounts_receivable(client_id);
CREATE INDEX idx_ar_status ON accounts_receivable(status);
CREATE INDEX idx_ar_due_date ON accounts_receivable(due_date);
