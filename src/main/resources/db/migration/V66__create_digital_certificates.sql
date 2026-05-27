CREATE TABLE IF NOT EXISTS digital_certificates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    certificate_data BYTEA NOT NULL,
    password_encrypted VARCHAR(255),
    valid_until DATE NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

ALTER TABLE company_config ADD COLUMN IF NOT EXISTS dian_resolution_id UUID REFERENCES dian_resolutions(id);
ALTER TABLE company_config ADD COLUMN IF NOT EXISTS software_pin VARCHAR(100);
ALTER TABLE company_config ADD COLUMN IF NOT EXISTS certificate_id UUID REFERENCES digital_certificates(id);
