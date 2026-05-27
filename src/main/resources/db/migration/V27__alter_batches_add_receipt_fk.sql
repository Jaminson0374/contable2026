-- ============================================================
-- V27: Vincula lotes (batches) con órdenes de compra y
-- entradas de mercancía (Sprint 5 — Compras).
-- Permite trazabilidad completa: OC → Receipt → Batch.
-- ============================================================

ALTER TABLE batches ADD COLUMN IF NOT EXISTS source_receipt_id UUID REFERENCES goods_receipts(id);
ALTER TABLE batches ADD COLUMN IF NOT EXISTS oc_id              UUID REFERENCES purchase_orders(id);

CREATE INDEX IF NOT EXISTS idx_batches_source_receipt ON batches(source_receipt_id);
CREATE INDEX IF NOT EXISTS idx_batches_oc              ON batches(oc_id);
