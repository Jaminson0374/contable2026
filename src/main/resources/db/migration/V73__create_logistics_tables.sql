CREATE TABLE IF NOT EXISTS logistics_receipts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    receipt_number VARCHAR(30) NOT NULL UNIQUE,
    receipt_date DATE NOT NULL,
    supplier_id UUID,
    purchase_order_id UUID,
    warehouse_id UUID NOT NULL REFERENCES warehouses(id),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING'
        CHECK (status IN ('PENDING','PARTIAL','COMPLETED','CANCELLED')),
    notes TEXT,
    created_by UUID REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    version BIGINT NOT NULL DEFAULT 0
);
CREATE INDEX IF NOT EXISTS idx_lr_warehouse ON logistics_receipts(warehouse_id);
CREATE INDEX IF NOT EXISTS idx_lr_supplier ON logistics_receipts(supplier_id);
CREATE INDEX IF NOT EXISTS idx_lr_status ON logistics_receipts(status);

CREATE TABLE IF NOT EXISTS logistics_receipt_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    receipt_id UUID NOT NULL REFERENCES logistics_receipts(id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES products(id),
    warehouse_id UUID NOT NULL REFERENCES warehouses(id),
    batch_id UUID REFERENCES batches(id),
    ordered_quantity NUMERIC(15,4),
    received_quantity NUMERIC(15,4) NOT NULL DEFAULT 0,
    unit_cost NUMERIC(15,4) NOT NULL DEFAULT 0,
    notes TEXT
);
CREATE INDEX IF NOT EXISTS idx_lri_receipt ON logistics_receipt_items(receipt_id);
CREATE INDEX IF NOT EXISTS idx_lri_product ON logistics_receipt_items(product_id);

CREATE TABLE IF NOT EXISTS logistics_pickings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    picking_number VARCHAR(30) NOT NULL UNIQUE,
    picking_date DATE NOT NULL,
    warehouse_id UUID NOT NULL REFERENCES warehouses(id),
    shipment_id UUID,
    sales_order_id UUID,
    status VARCHAR(20) NOT NULL DEFAULT 'PLANNED'
        CHECK (status IN ('PLANNED','IN_PROGRESS','COMPLETED','CANCELLED')),
    notes TEXT,
    created_by UUID REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    version BIGINT NOT NULL DEFAULT 0
);
CREATE INDEX IF NOT EXISTS idx_lp_warehouse ON logistics_pickings(warehouse_id);
CREATE INDEX IF NOT EXISTS idx_lp_status ON logistics_pickings(status);

CREATE TABLE IF NOT EXISTS logistics_picking_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    picking_id UUID NOT NULL REFERENCES logistics_pickings(id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES products(id),
    warehouse_id UUID NOT NULL REFERENCES warehouses(id),
    location_id UUID REFERENCES warehouse_locations(id),
    batch_id UUID REFERENCES batches(id),
    requested_quantity NUMERIC(15,4) NOT NULL DEFAULT 0,
    picked_quantity NUMERIC(15,4) NOT NULL DEFAULT 0,
    notes TEXT
);
CREATE INDEX IF NOT EXISTS idx_lpi_picking ON logistics_picking_items(picking_id);
CREATE INDEX IF NOT EXISTS idx_lpi_product ON logistics_picking_items(product_id);

CREATE TABLE IF NOT EXISTS logistics_shipments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    shipment_number VARCHAR(30) NOT NULL UNIQUE,
    shipment_date DATE NOT NULL,
    carrier_name VARCHAR(150),
    vehicle_plate VARCHAR(20),
    driver_name VARCHAR(150),
    transport_guide_id UUID,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT'
        CHECK (status IN ('DRAFT','CONFIRMED','IN_TRANSIT','DELIVERED','CANCELLED')),
    notes TEXT,
    created_by UUID REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    version BIGINT NOT NULL DEFAULT 0
);
CREATE INDEX IF NOT EXISTS idx_ls_status ON logistics_shipments(status);
CREATE INDEX IF NOT EXISTS idx_ls_guide ON logistics_shipments(transport_guide_id);

CREATE TABLE IF NOT EXISTS logistics_shipment_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    shipment_id UUID NOT NULL REFERENCES logistics_shipments(id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES products(id),
    picking_id UUID REFERENCES logistics_pickings(id),
    batch_id UUID REFERENCES batches(id),
    quantity NUMERIC(15,4) NOT NULL DEFAULT 0,
    notes TEXT
);
CREATE INDEX IF NOT EXISTS idx_lsi_shipment ON logistics_shipment_items(shipment_id);
CREATE INDEX IF NOT EXISTS idx_lsi_product ON logistics_shipment_items(product_id);

CREATE TABLE IF NOT EXISTS logistics_transport_guides (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    guide_number VARCHAR(30) NOT NULL UNIQUE,
    issue_date DATE NOT NULL,
    vehicle_plate VARCHAR(20),
    driver_name VARCHAR(150),
    driver_id VARCHAR(20),
    origin_address VARCHAR(300),
    destination_address VARCHAR(300),
    carrier_name VARCHAR(150),
    estimated_delivery DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'CREATED'
        CHECK (status IN ('CREATED','IN_TRANSIT','DELIVERED','CLOSED')),
    notes TEXT,
    created_by UUID REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    version BIGINT NOT NULL DEFAULT 0
);
CREATE INDEX IF NOT EXISTS idx_ltg_status ON logistics_transport_guides(status);
