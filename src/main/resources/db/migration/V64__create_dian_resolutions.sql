CREATE TABLE IF NOT EXISTS dian_resolutions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    resolution_number VARCHAR(50) NOT NULL,
    resolution_date DATE NOT NULL,
    valid_from DATE NOT NULL,
    valid_to DATE NOT NULL,
    prefix VARCHAR(10) NOT NULL DEFAULT '',
    range_from BIGINT NOT NULL,
    range_to BIGINT NOT NULL,
    software_pin VARCHAR(100),
    active BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
