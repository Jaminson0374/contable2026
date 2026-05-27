-- ============================================================
-- V29: Pagos a Proveedores (Sprint 5 — Compras)
-- Registra los pagos realizados a proveedores y su aplicación
-- a facturas específicas vía invoice_payments.
-- ============================================================

CREATE TABLE payments (
    id               UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    supplier_id      UUID           NOT NULL REFERENCES third_parties(id),
    amount           NUMERIC(15,2)  NOT NULL CHECK (amount > 0),
    payment_date     DATE           NOT NULL DEFAULT CURRENT_DATE,
    method           VARCHAR(50)    NOT NULL DEFAULT 'TRANSFERENCIA'
                     CHECK (method IN ('EFECTIVO','TRANSFERENCIA','CHEQUE','TARJETA','OTRO')),
    reference        VARCHAR(100),
    notes            TEXT,
    created_by       UUID           NOT NULL REFERENCES users(id),
    created_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    version          BIGINT         NOT NULL DEFAULT 0
);

CREATE TABLE invoice_payments (
    payment_id     UUID           NOT NULL REFERENCES payments(id) ON DELETE CASCADE,
    invoice_id     UUID           NOT NULL REFERENCES supplier_invoices(id),
    applied_amount NUMERIC(15,2)  NOT NULL CHECK (applied_amount > 0),
    PRIMARY KEY (payment_id, invoice_id)
);

CREATE INDEX idx_payments_supplier        ON payments(supplier_id);
CREATE INDEX idx_payments_date            ON payments(payment_date DESC);
CREATE INDEX idx_invoice_payments_invoice ON invoice_payments(invoice_id);
