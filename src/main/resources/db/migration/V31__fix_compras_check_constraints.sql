-- ============================================================
-- V31: Fix CHECK constraints in compras tables
-- Las migraciones V25, V26, V28 tenían CHECK constraints con
-- valores que no coinciden con los enums Java reales.
-- Esta migración los actualiza a los valores correctos sin
-- modificar archivos ya aplicados (evita checksum mismatch).
-- ============================================================

-- V25: purchase_orders.status — agregar PARTIAL, quitar APPROVED
ALTER TABLE purchase_orders DROP CONSTRAINT IF EXISTS purchase_orders_status_check;
ALTER TABLE purchase_orders ADD CONSTRAINT purchase_orders_status_check
    CHECK (status IN ('PENDING','PARTIAL','RECEIVED','CANCELLED'));

-- V26: goods_receipts.status — actualizar a COMPLETED, HIGH_COST_DEVIATION
ALTER TABLE goods_receipts DROP CONSTRAINT IF EXISTS goods_receipts_status_check;
ALTER TABLE goods_receipts ADD CONSTRAINT goods_receipts_status_check
    CHECK (status IN ('COMPLETED','HIGH_COST_DEVIATION'));

-- V28: supplier_invoices.status — actualizar a PENDING,RECONCILED,PAID,DISPUTED
ALTER TABLE supplier_invoices DROP CONSTRAINT IF EXISTS supplier_invoices_status_check;
ALTER TABLE supplier_invoices ADD CONSTRAINT supplier_invoices_status_check
    CHECK (status IN ('PENDING','RECONCILED','PAID','DISPUTED'));
