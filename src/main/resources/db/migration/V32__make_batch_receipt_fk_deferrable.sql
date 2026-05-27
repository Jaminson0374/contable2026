-- ============================================================
-- V32: Make batches.source_receipt_id FK deferrable
-- El use case CreateGoodsReceiptUseCase inserta el batch
-- ANTES del goods_receipt dentro de la misma transacción.
-- Un FK IMMEDIATE falla; DEFERRED se verifica al commit.
-- ============================================================

ALTER TABLE batches DROP CONSTRAINT IF EXISTS batches_source_receipt_id_fkey;
ALTER TABLE batches ADD CONSTRAINT batches_source_receipt_id_fkey
    FOREIGN KEY (source_receipt_id) REFERENCES goods_receipts(id)
    DEFERRABLE INITIALLY DEFERRED;
