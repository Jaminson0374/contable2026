-- ============================================================
-- V26: Entradas de Mercancía (Sprint 5 — Compras)
-- Registra la recepción física de mercancía contra una orden
-- de compra. Cada línea detalla producto, bodega y costo real.
-- ============================================================

CREATE TABLE goods_receipts (
    id               UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    oc_id            UUID           NOT NULL REFERENCES purchase_orders(id),
    receipt_date     DATE           NOT NULL DEFAULT CURRENT_DATE,
    status           VARCHAR(20)    NOT NULL DEFAULT 'COMPLETED'
                     CHECK (status IN ('DRAFT','COMPLETED','CANCELLED')),
    notes            TEXT,
    created_by       UUID           NOT NULL REFERENCES users(id),
    created_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    version          BIGINT         NOT NULL DEFAULT 0
);

CREATE TABLE receipt_line_items (
    id               UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    receipt_id       UUID           NOT NULL REFERENCES goods_receipts(id) ON DELETE CASCADE,
    product_id       UUID           NOT NULL REFERENCES products(id),
    warehouse_id     UUID           NOT NULL REFERENCES warehouses(id),
    received_qty     NUMERIC(15,3)  NOT NULL CHECK (received_qty > 0),
    actual_cost      NUMERIC(15,2)  NOT NULL CHECK (actual_cost >= 0),
    UNIQUE(receipt_id, product_id)
);

CREATE INDEX idx_goods_receipts_oc   ON goods_receipts(oc_id);
CREATE INDEX idx_goods_receipts_date ON goods_receipts(receipt_date DESC);
