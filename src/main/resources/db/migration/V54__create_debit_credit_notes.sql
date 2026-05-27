-- ============================================================
-- V54: Notas débito/crédito (Sprint 10 — Compras)
-- Entidad independiente para ajustes de saldo a proveedores.
-- FK opcional a supplier_invoices (referencial puro).
-- ============================================================

CREATE TABLE IF NOT EXISTS debit_credit_notes (
    id                  UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    type                VARCHAR(20)     NOT NULL CHECK (type IN ('DEBIT_NOTE','CREDIT_NOTE')),
    supplier_id         UUID            NOT NULL REFERENCES third_parties(id),
    supplier_invoice_id UUID            REFERENCES supplier_invoices(id),
    document_number     VARCHAR(50)     NOT NULL,
    amount              NUMERIC(15,2)   NOT NULL CHECK (amount > 0),
    reason              TEXT,
    reference           VARCHAR(100),
    created_by          UUID            REFERENCES users(id),
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    version             BIGINT          NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_dcn_supplier ON debit_credit_notes(supplier_id);
CREATE INDEX IF NOT EXISTS idx_dcn_invoice  ON debit_credit_notes(supplier_invoice_id);
