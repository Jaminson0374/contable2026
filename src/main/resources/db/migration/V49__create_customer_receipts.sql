CREATE TABLE customer_receipts (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES third_parties(id),
    amount NUMERIC(15,2) NOT NULL CHECK (amount > 0),
    payment_date DATE NOT NULL,
    method VARCHAR(20) NOT NULL CHECK (method IN ('CASH','TRANSFER','CARD','CHECK')),
    reference VARCHAR(100),
    notes TEXT,
    created_by VARCHAR(100),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE receipt_applications (
    id UUID PRIMARY KEY,
    receipt_id UUID NOT NULL REFERENCES customer_receipts(id),
    ar_id UUID NOT NULL REFERENCES accounts_receivable(id),
    applied_amount NUMERIC(15,2) NOT NULL CHECK (applied_amount > 0)
);

CREATE INDEX idx_cr_client ON customer_receipts(client_id);
CREATE INDEX idx_cr_date ON customer_receipts(payment_date);
CREATE INDEX idx_ra_receipt ON receipt_applications(receipt_id);
