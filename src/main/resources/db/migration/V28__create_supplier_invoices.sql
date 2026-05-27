-- ============================================================
-- V28: Facturas de Proveedor (Sprint 5 — Compras)
-- Registra las facturas de compra emitidas por proveedores.
-- Relación N:N con órdenes de compra vía invoice_orders.
-- Los totales se calculan al momento de registrar la factura.
-- ============================================================

CREATE TABLE supplier_invoices (
    id               UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    supplier_id      UUID           NOT NULL REFERENCES third_parties(id),
    invoice_number   VARCHAR(50)    NOT NULL,
    issue_date       DATE           NOT NULL,
    due_date         DATE,
    subtotal         NUMERIC(15,2)  NOT NULL DEFAULT 0 CHECK (subtotal >= 0),
    iva_total        NUMERIC(15,2)  NOT NULL DEFAULT 0 CHECK (iva_total >= 0),
    retention_total  NUMERIC(15,2)  NOT NULL DEFAULT 0 CHECK (retention_total >= 0),
    total            NUMERIC(15,2)  NOT NULL DEFAULT 0 CHECK (total >= 0),
    status           VARCHAR(20)    NOT NULL DEFAULT 'PENDING'
                     CHECK (status IN ('PENDING','PAID','CANCELLED','OVERDUE')),
    notes            TEXT,
    created_by       UUID           NOT NULL REFERENCES users(id),
    created_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    version          BIGINT         NOT NULL DEFAULT 0,
    UNIQUE(supplier_id, invoice_number)
);

CREATE TABLE invoice_orders (
    invoice_id UUID NOT NULL REFERENCES supplier_invoices(id) ON DELETE CASCADE,
    oc_id      UUID NOT NULL REFERENCES purchase_orders(id),
    PRIMARY KEY (invoice_id, oc_id)
);

-- current_balance ya existe desde V8; IF NOT EXISTS asegura idempotencia
ALTER TABLE third_parties ADD COLUMN IF NOT EXISTS current_balance NUMERIC(15,2) NOT NULL DEFAULT 0;

CREATE INDEX idx_supplier_invoices_supplier ON supplier_invoices(supplier_id);
CREATE INDEX idx_supplier_invoices_status   ON supplier_invoices(status);
CREATE INDEX idx_supplier_invoices_date     ON supplier_invoices(issue_date DESC);
