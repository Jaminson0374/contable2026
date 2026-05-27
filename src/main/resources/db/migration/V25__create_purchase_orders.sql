-- ============================================================
-- V25: Órdenes de Compra (Sprint 5 — Compras)
-- Registra las órdenes de compra a proveedores y sus líneas
-- de detalle por producto y bodega.
-- ============================================================

CREATE TABLE purchase_orders (
    id               UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    supplier_id      UUID           NOT NULL REFERENCES third_parties(id),
    status           VARCHAR(20)    NOT NULL DEFAULT 'PENDING'
                     CHECK (status IN ('PENDING','APPROVED','RECEIVED','CANCELLED')),
    order_date       DATE           NOT NULL DEFAULT CURRENT_DATE,
    notes            TEXT,
    created_by       UUID           NOT NULL REFERENCES users(id),
    created_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    version          BIGINT         NOT NULL DEFAULT 0
);

CREATE TABLE purchase_line_items (
    id               UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    oc_id            UUID           NOT NULL REFERENCES purchase_orders(id) ON DELETE CASCADE,
    product_id       UUID           NOT NULL REFERENCES products(id),
    warehouse_id     UUID           NOT NULL REFERENCES warehouses(id),
    ordered_qty      NUMERIC(15,3)  NOT NULL CHECK (ordered_qty > 0),
    received_qty     NUMERIC(15,3)  NOT NULL DEFAULT 0 CHECK (received_qty >= 0),
    unit_cost        NUMERIC(15,2)  NOT NULL CHECK (unit_cost >= 0),
    line_number      INT            NOT NULL DEFAULT 0,
    UNIQUE(oc_id, product_id)
);

CREATE INDEX idx_purchase_orders_supplier ON purchase_orders(supplier_id);
CREATE INDEX idx_purchase_orders_status   ON purchase_orders(status);
CREATE INDEX idx_purchase_orders_date     ON purchase_orders(order_date DESC);
CREATE INDEX idx_purchase_line_items_oc   ON purchase_line_items(oc_id);
