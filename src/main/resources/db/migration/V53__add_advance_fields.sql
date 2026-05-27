-- ============================================================
-- V53: Anticipos a Proveedores (Sprint 10 — Compras)
-- Agrega soporte para anticipos (pagos por adelantado) y
-- la tabla de aplicaciones de anticipos a facturas.
-- ============================================================

ALTER TABLE payments ADD COLUMN IF NOT EXISTS is_advance BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE payments ADD COLUMN IF NOT EXISTS remaining_advance NUMERIC(15,2);

CREATE TABLE IF NOT EXISTS advance_applications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    advance_payment_id UUID NOT NULL REFERENCES payments(id),
    invoice_id UUID NOT NULL REFERENCES supplier_invoices(id),
    applied_amount NUMERIC(15,2) NOT NULL CHECK (applied_amount > 0),
    application_date DATE NOT NULL DEFAULT CURRENT_DATE,
    created_by UUID REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_adv_app_advance ON advance_applications(advance_payment_id);
