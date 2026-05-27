-- ============================================================
-- V30: Índices complementarios — Compras (Sprint 5)
-- Índices compuestos y de cobertura para consultas frecuentes
-- del módulo de compras. Todos usan IF NOT EXISTS para ser
-- seguros frente a índices ya creados en migraciones previas.
-- ============================================================

-- Búsqueda combinada: proveedor + estado de factura
CREATE INDEX IF NOT EXISTS idx_supplier_invoices_supplier_status ON supplier_invoices(supplier_id, status);

-- Búsqueda por proveedor y fecha (facturas vencidas, aging)
CREATE INDEX IF NOT EXISTS idx_supplier_invoices_supplier_date ON supplier_invoices(supplier_id, due_date);

-- Líneas de entrada por recepción (no creado en V26)
CREATE INDEX IF NOT EXISTS idx_receipt_line_items_receipt ON receipt_line_items(receipt_id);

-- Cobertura para joins payment ↔ invoice más frecuentes
CREATE INDEX IF NOT EXISTS idx_invoice_payments_payment ON invoice_payments(payment_id);

-- Proveedor + fecha de pago (historial de pagos por proveedor)
CREATE INDEX IF NOT EXISTS idx_payments_supplier_date ON payments(supplier_id, payment_date DESC);
